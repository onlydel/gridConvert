/* 유니코드 */
/*
function initWiseGrid(objName, width, height) {
    var WISEGRID_TAG = "<OBJECT ID='" + objName + "'codebase='../../WiseGrid/WiseGridU_v5_3_1_88.cab#version=5,3,1,88";
    WISEGRID_TAG = WISEGRID_TAG + " NAME='" + objName + "' WIDTH=" + width + " HEIGHT=" + height + " border=0";
    WISEGRID_TAG     = WISEGRID_TAG + " CLASSID='CLSID:0CE50171-51F4-4b1e-992B-4ECC8E0BE537'>";
    WISEGRID_TAG = WISEGRID_TAG + " <PARAM NAME = 'strLicenseKeyList' VALUE='8BA4276E9CF2E8B2BBA5F636F384FD4A'>";
    WISEGRID_TAG = WISEGRID_TAG + "</OBJECT>";
    document.write(WISEGRID_TAG);
}
*/



/* 일반 */
function initWiseGrid(objName, width, height) {
    //var WISEGRID_TAG = "<OBJECT ID='" + objName + "' codebase='/WiseGrid/WiseGrid_v5_3_1_1.cab#version=5,3,1,1'";
    var WISEGRID_TAG = "<OBJECT ID='" + objName + "' codebase='/WiseGrid/WiseGrid_v5_3_1_88.cab#version=5,3,1,88'";
    WISEGRID_TAG = WISEGRID_TAG + " NAME='" + objName + "' WIDTH=" + width + " HEIGHT=" + height + " border=0";
    WISEGRID_TAG = WISEGRID_TAG + " CLASSID='CLSID:E8AA1760-8BE5-4891-B433-C53F7333710F'>";
    WISEGRID_TAG = WISEGRID_TAG + " <PARAM NAME = 'strLicenseKeyList' VALUE='122670CA8BD07EF9CD21AC63B6EB6E08'>";
    WISEGRID_TAG = WISEGRID_TAG + "</OBJECT>"
    document.write(WISEGRID_TAG);
}


/* 버튼 */
function btn(a, b) {
    document.write("<table cellpadding=0 cellspacing=0 border=0 height=23 onClick=" + a + " style=cursor:hand><tr><td width=15 class=btn_left>");
    document.write("<img src=../images/blank.gif width=15 height=1></td>");
    document.write("<td class=btn_txt valign=middle>" + b + "</td><td width=4 class=btn_right><img src=../images/blank.gif width=4></td></tr></table>");
}