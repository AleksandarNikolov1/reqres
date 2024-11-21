package in.reqres.models.response;

import in.reqres.models.pojo.SupportData;
import in.reqres.models.pojo.UserData;

public class SingleUserResponse {
    private UserData data;
    private SupportData support;

    public UserData getData() {
        return data;
    }

    public SupportData getSupport() {
        return support;
    }
}
