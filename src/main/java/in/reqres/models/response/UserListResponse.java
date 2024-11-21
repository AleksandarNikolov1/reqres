package in.reqres.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.reqres.models.pojo.UserData;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserListResponse {
    private List<UserData> data;

    public List<UserData> getData() {
        return data;
    }
}
