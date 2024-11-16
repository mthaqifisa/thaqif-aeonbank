package com.thaqif.aeonbank.dto.borrower;

import com.thaqif.aeonbank.dto.common.CommonResponse;

import java.util.List;

public class BorrowerResponse extends CommonResponse {
    private List<BorrowerResponseData> data;

    public static class BorrowerResponseData {
        private Long id;
        private String name;
        private String email;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public List<BorrowerResponseData> getData() {
        return data;
    }

    public void setData(List<BorrowerResponseData> data) {
        this.data = data;
    }
}
