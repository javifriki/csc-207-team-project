package interface_adaptor.add_asset_and_liability;

import interface_adaptor.ViewModel;

public class AddAssetAndLiabilityViewModel extends ViewModel<AddAssetAndLiabilityState> {
    public AddAssetAndLiabilityViewModel() {
        setState(new AddAssetAndLiabilityState());
    }
}
