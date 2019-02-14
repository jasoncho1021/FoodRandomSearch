package com.aone.menurandomchoice.views.ownerstore;

import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.StoreDetail;
import com.aone.menurandomchoice.views.base.BaseContract;

public interface OwnerStoreContract {

    interface View extends BaseContract.View {

        void showStoreDetail(StoreDetail storeDetail);

        void showErrorStoreDetail(StoreDetail storeDetail, String errorMessage);

        void moveToOwnerEditPage(StoreDetail storeDetail);

        void moveToMenuPreviewPage(MenuDetail menuDetail);

    }

    interface Presenter extends BaseContract.Presenter<OwnerStoreContract.View> {

        void loadStoreDetail(int storeIdx);

        void onMenuDetailClick(MenuDetail menuDetail);

        void onMapClick();

        void stopNetwork();

    }
}