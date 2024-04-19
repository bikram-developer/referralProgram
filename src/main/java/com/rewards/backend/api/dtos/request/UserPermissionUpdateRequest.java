package com.rewards.backend.api.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPermissionUpdateRequest {
    private boolean isBanned;
    private boolean isLocked;
    private boolean isFreezed;
    private boolean isActive;
    private boolean referralStatus;
    private boolean rewardAccess;
}
