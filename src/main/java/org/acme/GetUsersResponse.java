package org.acme;

import com.google.common.base.MoreObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetUsersResponse {

    private static final Logger log = LoggerFactory.getLogger(GetUsersResponse.class);

    private List<GetSingleUserResponse> users = new ArrayList<>();

    public GetUsersResponse() {
    }

    public GetUsersResponse(List<User> us) {
        this.users = us.stream().map(u -> new GetSingleUserResponse(u))
                .collect(Collectors.toList());

        log.info("new GetUsersResponse(): {}", this.users);
    }

    public List<GetSingleUserResponse> getUsers() {
        return users;
    }

    public void setUsers(List<GetSingleUserResponse> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("users", users).toString();
    }
}
