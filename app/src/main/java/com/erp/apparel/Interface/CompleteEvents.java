package com.erp.apparel.Interface;


import com.erp.apparel.Models.ProcessLightModel;
import com.erp.apparel.Models.ProcessLightOngoingModel;
import com.erp.apparel.Models.ProcessLightUpcomingModel;
import com.erp.apparel.Models.ViewDelayedModel;
import com.erp.apparel.Models.ViewOngoingModel;
import com.erp.apparel.Models.ViewUpcomingModel;

public interface CompleteEvents {

        void onDelayedCompleteClick(ViewDelayedModel demo, int positon);
        void onOnTimeCompleteClick(ViewOngoingModel demo, int positon);

        void onUpcomingCompleteClick(ViewUpcomingModel demo, int positon);

}
