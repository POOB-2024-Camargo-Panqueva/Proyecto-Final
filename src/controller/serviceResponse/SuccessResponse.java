package controller.serviceResponse;

public final class SuccessResponse<T> extends ServiceResponse<T>{
    public SuccessResponse(T payload, String message){
        super(payload, true, message);
    }

}
