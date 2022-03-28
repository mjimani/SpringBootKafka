package ir.mjimani.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Response<T> {
    private T data;
    private Boolean success;
    private List<String> messages;

    public Response() {
        success = true;
    }

    public Response(T data) {
        success = true;
        this.data = data;
    }

    public Response<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Response<T> setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public Response<T> setMessages(List<String> messages) {
        this.messages = messages;
        return this;
    }

    public Response<T> addMessage(String message) {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        messages.add(message);
        return this;
    }
}
