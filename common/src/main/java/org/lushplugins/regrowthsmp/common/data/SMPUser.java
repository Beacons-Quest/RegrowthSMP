package org.lushplugins.regrowthsmp.common.data;

import org.jetbrains.annotations.Nullable;

public interface SMPUser {

    @Nullable UserData getUserData(String module);
}
