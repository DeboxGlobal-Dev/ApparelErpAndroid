package com.erp.apparel.Interface;


import com.erp.apparel.Activities.ProcessLight;
import com.erp.apparel.Models.ProcessLightModel;
import com.erp.apparel.Models.ProcessLightOngoingModel;
import com.erp.apparel.Models.ProcessLightUpcomingModel;
import com.erp.apparel.Models.ViewDelayedModel;

public interface ViewEvents {

        void onDelayedClick(ProcessLightModel demo, int positon);

        void onOngoingClick(ProcessLightOngoingModel demo, int positon);

        void onUpcomingClick(ProcessLightUpcomingModel demo, int positon);

}
