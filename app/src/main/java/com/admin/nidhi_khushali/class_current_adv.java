package com.admin.nidhi_khushali;

//import in.prsc.com.test_bin.util.Util;

import com.util.Util;

public class class_current_adv {

    int arrId[]={1,2,3};
    String arrAdv_no[]={"123ABC","234DFG","456TGH"};
    String arrAdv_desc[]={"xxxxx","yyyyyyyy","zzz"};
    String arrAdv_Attchment[]={Util.url_pdf,"atch2","atch3"};
    String arrAdv_date_created[]={"22/2/2108","25/2/2018","18/6/2018"};
    String arrAdv_status[]={"Open","Open","Open"};
    String arrAdv_noOfDiv[]={"1","5","3"};
    String arrAdv_edit[]={"Click","Click","Click"};
    String arrAdv_delete[]={"Click","Click","Click"};



    public int getId(int i) {
        return arrId[i];
    }

    public String getAdv_no(int i) {
        return arrAdv_no[i];
    }

    public String getAdv_desc(int i) {
        return arrAdv_desc[i];
    }

    public String getAdv_attachment(int i) {
        return arrAdv_Attchment[i];
    }

    public String getAdv_date_created(int i) {
        return arrAdv_date_created[i];
    }

    public String getAdv_status(int i) {
        return arrAdv_status[i];
    }

    public String getAdv_noOfDiv(int i) {
        return arrAdv_noOfDiv[i];
    }

    public String getAdv_edit(int i) {
        return arrAdv_edit[i];
    }

    public String getAdv_delete(int i) {
        return arrAdv_delete[i];
    }
}
