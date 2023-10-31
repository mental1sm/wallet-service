package com.wallet.utility.json.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommonResponse {
    private Object data;
}
