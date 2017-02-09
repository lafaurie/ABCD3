package cu.uci.abcd.cataloguing.util;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;

public class LoanObjectField2 {

	/**
	 * Created by Basilio Puentes Rodríguez
	 */

	private Combo locationCombo;
	private Combo situationCombo;
	private Combo providerCombo;
	private Combo acquisitionCoinType;
	private Combo loanObjectType;
	private Text inventoryNumberText;
	private Text tomeText;
	private Text volumeText;
	private Combo noRecommendCombo;
	private Text priceText;
	//private Combo noOrderCombo;
	private Text conditionsText;
	private Text donationEstimatedPriceText;
	private Text redeemendByText;
	private Text redeemendEstimatedPriceText;

	private CTabFolder tabFolder;
/*	private final int BUY = 0;
	private final int DONATION = 1;
	private final int EXCHANGE = 2;
	private Control price;*/
	private Button buttonSave;

	public void setButtonSave(Button buttonSave){
		this.buttonSave = buttonSave;
	}

	public Button getButtonSave(){
		return this.buttonSave;
	}

	public Combo getLocationCombo() {
		return locationCombo;
	}
	public void setLocationCombo(Combo locationCombo) {
		this.locationCombo = locationCombo;
	}

	public Combo getSituationCombo() {
		return situationCombo;
	}
	public void setSituationCombo(Combo situationCombo) {
		this.situationCombo = situationCombo;
	}

	public Combo getProviderCombo() {
		return providerCombo;
	}
	public void setProviderCombo(Combo providerCombo) {
		this.providerCombo = providerCombo;
	}

	public Combo getAcquisitionCoinType() {
		return acquisitionCoinType;
	}
	public void setAcquisitionCoinType(Combo acquisitionCoinType) {
		this.acquisitionCoinType = acquisitionCoinType;
	}

	public void setLoanObjectType(Combo loanObjectType){
		this.loanObjectType = loanObjectType;
	}
	public Combo getLoanObjectType(){
		return this.loanObjectType;
	}

	public Text getInventoryNumberText() {
		return inventoryNumberText;
	}
	public void setInventoryNumberText(Text inventoryNumberText) {
		this.inventoryNumberText = inventoryNumberText;
	}

	public Text getTomeText() {
		return tomeText;
	}
	public void setTomeText(Text tomeText) {
		this.tomeText = tomeText;
	}

	public Text getVolumeText() {
		return volumeText;
	}
	public void setVolumeText(Text volumeText) {
		this.volumeText = volumeText;
	}

	//Buy
	public Combo getNoRecommendCombo() {
		return noRecommendCombo;
	}
	public void setNoRecommendCombo(Combo noRecommendCombo) {
		this.noRecommendCombo = noRecommendCombo;
	}

	public Text getPriceText() {
		return priceText;
	}
	public void setPriceText(Text priceText) {
		this.priceText = priceText;
	}

	/*public Combo getNoOrderCombo() {
		return noOrderCombo;
	}
	public void setNoOrderCombo(Combo noOrderCombo) {
		this.noOrderCombo = noOrderCombo;
	}*/

	//Donation
	public Text getConditionsText() {
		return conditionsText;
	}
	public void setConditionsText(Text conditionsText) {
		this.conditionsText = conditionsText;
	}

	public Text getDonationEstimatedPriceText() {
		return donationEstimatedPriceText;
	}
	public void setDonationEstimatedPriceText(Text donationEstimatedPriceText) {
		this.donationEstimatedPriceText = donationEstimatedPriceText;
	}

	//Redeemend
	public Text getRedeemendByText() {
		return redeemendByText;
	}
	public void setRedeemendByText(Text redeemendByText) {
		this.redeemendByText = redeemendByText;
	}

	public Text getRedeemendEstimatedPriceText() {
		return redeemendEstimatedPriceText;
	}
	public void setRedeemendEstimatedPriceText(
			Text redeemendEstimatedPriceText) {
		this.redeemendEstimatedPriceText = redeemendEstimatedPriceText;
	}

	public void resetValues(){
		this.locationCombo.select(0);
		this.situationCombo.select(0);
		this.providerCombo.select(0);
		this.acquisitionCoinType.select(0);
		this.loanObjectType.select(0);
		this.inventoryNumberText.setText("");
		this.tomeText.setText("");
		this.volumeText.setText("");
		this.noRecommendCombo.select(0);
		this.priceText.setText("");
		//this.noOrderCombo.select(0);
		this.conditionsText.setText("");
		this.donationEstimatedPriceText.setText("");
		this.redeemendByText.setText("");
		this.redeemendEstimatedPriceText.setText("");
	}

	public void resetValues(Combo combo){
		combo.removeAll();
	}

	//validate
	public boolean validate(){
		boolean result = false;

		int value = this.tabFolder.getSelectionIndex();

		if(value != -1)
			result = true;
			
		paint(result);

		return result;
	}

	private void paint(boolean val){
		Color color;

		if(!val)
			color = new Color(this.tabFolder.getDisplay(), 255,204,153);	
		else
			color = new Color(this.acquisitionCoinType.getDisplay(), 239,239,239);

		this.tabFolder.setBackground(color);
	}

/*	private boolean price(String value){
		boolean result = false;	
		if(!value.equals("") && value != null)
			result = true;
		return result;
	}*/
	public CTabFolder getTabFolder() {
		return tabFolder;
	}
	public void setTabFolder(CTabFolder tabFolder) {
		this.tabFolder = tabFolder;
	}

}
