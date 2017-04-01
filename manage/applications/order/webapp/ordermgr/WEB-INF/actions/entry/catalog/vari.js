var DET = new Array(2);
var IMG = new Array(2);
var OPT = new Array(2);
var VIR = new Array(0);
var detailImageUrl = null;
OPT[0] = "FTCOLOR";
OPT[1] = "FTDIMENSION";
function listCOLOR() {
    document.forms["addform"].elements["FTCOLOR"].options.length = 1;
    document.forms["addform"].elements["FTCOLOR"].options[0] = new Option("颜色", "", true, true);
    document.forms["addform"].elements["FTCOLOR"].options.length = 1;
    document.forms["addform"].elements["FTDIMENSION"].options.length = 1;
    document.forms["addform"].elements["FTCOLOR"].options[1] = new Option("红色", "0");
    DET[0] = "";
    IMG[0] = "/images/products/10010/large.jpg";
    document.forms["addform"].elements["FTCOLOR"].options[2] = new Option("蓝色", "1");
    DET[1] = "";
    IMG[1] = "/images/products/10010/large.jpg";
}
function listFTDIMENSION0() {
    document.forms["addform"].elements["FTDIMENSION"].options.length = 1;
    document.forms["addform"].elements["FTDIMENSION"].options[0] = new Option("尺寸", "", true, true);
    document.forms["addform"].elements["FTDIMENSION"].options[1] = new Option("中号", "10010REMAX");
    document.forms["addform"].elements["FTDIMENSION"].options[2] = new Option("小号", "10010REMIN");
}
function listFTDIMENSION1() {
    document.forms["addform"].elements["FTDIMENSION"].options.length = 1;
    document.forms["addform"].elements["FTDIMENSION"].options[0] = new Option("尺寸", "", true, true);
    document.forms["addform"].elements["FTDIMENSION"].options[1] = new Option("中号", "10010BLMAX");
}
function checkAmtReq(sku) {
    if (sku == "10010REMIN") return "N";
    if (sku == "10010REMAX") return "N";
    if (sku == "10010BLMIN") return "N";
    if (sku == "10010BLMAX") return "N";
    if (sku == "10010YEMIN") return "N";
    if (sku == "10010YEMAX") return "N";
}
function getVariantPrice(sku) {
    if (sku == "10010REMIN") return "￥1,054.00";
    if (sku == "10010REMAX") return "￥138.00";
    if (sku == "10010BLMIN") return "￥37.00";
    if (sku == "10010BLMAX") return "￥199.00";
    if (sku == "10010YEMIN") return "￥37.00";
    if (sku == "10010YEMAX") return "￥37.00";
}