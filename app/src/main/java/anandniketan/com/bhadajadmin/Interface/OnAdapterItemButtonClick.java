package anandniketan.com.bhadajadmin.Interface;

public interface OnAdapterItemButtonClick {

    enum Action {ADD, DELETE, UPDATE, MODIFY, APPROVE, REJECT}

    void onItemButtonClick(Action target,int posID);
}
