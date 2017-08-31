package top.omooo.admin.btscoure;

/**
 * Created by Omooo on 2017/8/29.
 */

public class ItemBean {
    public String item_Title;
    public String item_Date;
    public String item_Size;
    public int item_Color;

    public ItemBean(String item_Title, String item_Date, String item_Size,int item_Color) {
        this.item_Title = item_Title;
        this.item_Date = item_Date;
        this.item_Size = item_Size;
        this.item_Color = item_Color;
    }
}
