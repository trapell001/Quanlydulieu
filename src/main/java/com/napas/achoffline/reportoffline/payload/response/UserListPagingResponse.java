/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.napas.achoffline.reportoffline.payload.response;

import com.napas.achoffline.reportoffline.models.UserShortInfo;
import java.util.List;

/**
 *
 * @author huynx
 */
public class UserListPagingResponse {

    private long totalRows;
    private int totalPages;
    private List<UserShortInfo> listUsers;

    public UserListPagingResponse() {
    }

    public UserListPagingResponse(long totalRows, int totalPages, List<UserShortInfo> listUsers) {
        this.totalRows = totalRows;
        this.totalPages = totalPages;
        this.listUsers = listUsers;
    }

    public long getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(long totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<UserShortInfo> getListUsers() {
        return listUsers;
    }

    public void setListUsers(List<UserShortInfo> listUsers) {
        this.listUsers = listUsers;
    }

}
