package com.vowing.purchase.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 게시물 찾을 수 없음
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PurchaseNotFound extends RuntimeException{}
