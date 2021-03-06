package com.aone.menurandomchoice.views.ownerstore;

import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.repository.model.UserAccessInfo;
import com.aone.menurandomchoice.views.base.BaseContract;

public interface OwnerStoreContract {

    interface View extends BaseContract.View {

        void showStoreDetail(StoreDetail storeDetail);

        void moveToOwnerEditPage(StoreDetail storeDetail);

        void moveToMapDetailPage(double latitude, double longitude);

        void moveToMenuPreviewPage(MenuDetail menuDetail);

        void finishOwnerStorePage();
    }

    interface Presenter extends BaseContract.Presenter<OwnerStoreContract.View> {

        void loadStoreDetail(UserAccessInfo userAccessInfo);

        void onMenuDetailClick(android.view.View view, MenuDetail menuDetail);

        void onMapClick(double latitude, double longitude);

        void stopNetwork();

        void onLogoutClick(android.view.View view);

    }

}
