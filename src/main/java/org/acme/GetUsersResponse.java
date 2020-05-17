package org.acme;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetUsersResponse {

    private static final Logger log = LoggerFactory.getLogger(GetUsersResponse.class);

    private List<User> users = new ArrayList<>();
}
