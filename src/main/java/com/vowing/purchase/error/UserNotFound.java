package com.vowing.purchase.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 사용자를 찾을 수 없음
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFound extends RuntimeException {}
