package com.taskflow.global.aop;

import com.taskflow.domain.activitylog.entity.ActivityType;
import com.taskflow.domain.activitylog.service.ActivityService;
import com.taskflow.domain.auth.dto.login.LoginRequestDto;
import com.taskflow.domain.member.service.MemberService;
import com.taskflow.global.annotation.LogActivity;
import com.taskflow.global.common.ApiResponse;
import com.taskflow.global.common.HasId;
import com.taskflow.global.config.customUserDetails.Entity.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Aspect
@Component
@RequiredArgsConstructor
public class ActivityLogAspect {

    private final ActivityService activityService;
    private final MemberService memberService;
    private final HttpServletRequest request;

    @Around("@annotation(logActivity)")
    public Object logAction(ProceedingJoinPoint joinPoint, LogActivity logActivity) throws Throwable {
        Object result = joinPoint.proceed();

        Long userId = getUserId(joinPoint, logActivity);
        String ip = request.getRemoteAddr();
        String url = request.getRequestURI();
        String httpMethod = request.getMethod();

        Long targetId;

        if (logActivity.value().name().startsWith("USER_")) {
            targetId = userId; // 사용자 활동일 경우 본인 ID가 타겟
        } else {
            targetId = extractTargetIdFromArgs(joinPoint, logActivity.target());
            if (targetId == null) {
                targetId = extractTargetIdFromReturn(result);
            }
        }

        activityService.saveLog(userId, ip, httpMethod, url, logActivity.value(), targetId);

        return result;
    }

    private Long getUserId(ProceedingJoinPoint joinPoint, LogActivity logActivity) {
        Long userId = null;

        // 만약에 로그인인 경우에는 SecurityContextHolder에 Id가 없으니까 requestDto 에서 username을 사용해 member를 꺼내서 거기서 memberId를 추출한다.
        if (logActivity.value() == ActivityType.USER_LOGGED_IN){
            for (Object arg : joinPoint.getArgs()) { // 메서드의 매개변수의 값 빼오기
                if (arg instanceof LoginRequestDto requestDto) { // 매개변수가 LoginRequestDto 인지 확인
                userId = memberService.findByUsernameOrElseThrow(requestDto.getUsername()).getId(); // userId 추출하기
                }
            }
        } else {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 로그인한 상태면 SecurityContextHolder 에서 userId 추출
            if (principal instanceof CustomUserDetails userDetails) {
                userId = userDetails.getId();
            }
        }
        return userId;
    }


    /**
     * 메서드 파라미터 중에서, @LogActivity(target = "xxx")에서 지정한 파라미터 이름과
     * 일치하는 Long 타입의 값을 찾아 targetId로 반환합니다.
     *
     * 예시:
     *   @LogActivity(value = ActivityType.COMMENT_DELETED, target = "commentId")
     *   public ResponseEntity<?> deleteComment(@PathVariable Long taskId, @PathVariable Long commentId)
     *
     * → paramNames = ["taskId", "commentId"]
     * → args       = [1L, 99L]
     * → paramName  = "commentId"
     * → return     = 99L
     *
     * @param joinPoint 현재 AOP가 감싸고 있는 메서드 실행 정보
     * @param paramName @LogActivity(target = "xxx") 에서 지정된 파라미터 이름
     * @return 해당 파라미터 이름에 해당하는 Long 값 (없으면 null)
     */
    private Long extractTargetIdFromArgs(ProceedingJoinPoint joinPoint, String paramName) {
        // 1. 메서드 파라미터 이름들을 배열로 가져옴
        String[] paramNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();

        // 2. 메서드 호출 시 실제 전달된 인자 값들
        Object[] args = joinPoint.getArgs();

        // 3. 각 파라미터 이름과 값을 순회하면서
        for (int i = 0; i < paramNames.length; i++) {
            // 4. 지정된 이름과 동일한 파라미터가 있다면
            if (paramNames[i].equals(paramName)) {
                Object value = args[i];
                // 5. 그 값이 Long 타입이면 targetId로 사용
                if (value instanceof Long id) {
                    return id;
                }
            }
        }

        // 6. 해당 이름의 Long 타입 파라미터가 없다면 null 반환
        return null;
    }

    // 리턴값에서 targetId 추출
    private Long extractTargetIdFromReturn(Object result) {
        // 1. 컨트롤러의 리턴값이 null인 경우 -> 바로 null 반환
        if (result == null) {
            return null;
        }

        // 2. 리턴값이 ResponseEntity<?> 타입인지 확인
        if (result instanceof ResponseEntity<?> responseEntity) {
            // 3. ResponseEntity에서 본문(body) 추출
            Object body = responseEntity.getBody();

            // 4. 본문이 ApiResponse<?> 타입인지 확인
            if (body instanceof ApiResponse<?> apiResponse) {
                // 5. ApiResponse 안의 data 필드 추출
                Object data = apiResponse.getData();

                // 6. data가 HasId 인터페이스를 구현한 객체인지 확인
                if (data instanceof HasId hasIdData) {
                    // 7. getId() 호출을 통해 targetId 추출
                    Long targetId = hasIdData.getId();
                    return targetId;
                }
            }
        }

        return null;
    }
}
