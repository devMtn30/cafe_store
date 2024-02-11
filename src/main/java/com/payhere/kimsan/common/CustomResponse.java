package com.payhere.kimsan.common;

import co.elastic.clients.elasticsearch.nodes.Http;
import com.payhere.kimsan.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class CustomResponse<T> {
    private Meta meta;
    private T data;

    public void setMeta(HttpStatus code, String message) {
        this.meta = new Meta(code.value(), message);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Meta {
        private int code;
        private String message;

    }

    public CustomResponse(HttpStatus status, T data) {
        this.meta = new Meta(status.value(), status.name());
        this.data = data;
    }
}