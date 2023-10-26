package aug.laundry.enums.fileUpload;

public enum FileUploadType {
    DELIVERY("DELIVERY", "delivery_images"),
    REPAIR("REPAIR", "repair_images"),
    INSPECTION("INSPECTION", "inspection_images");

    private final String tableType;
    private final String folderName;


    FileUploadType(String tableType, String folderName) {
        this.tableType = tableType;
        this.folderName = folderName;
    }

    public String getTableType(){
        return tableType;
    }
    public String getfolderName(){
        return folderName;
    }


}
