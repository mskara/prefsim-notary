package com.raccoon.prefsimnotary.model.document.embedded;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Contact {

    private String city;
    private String district;
    private String address;
    private String phone;

}
