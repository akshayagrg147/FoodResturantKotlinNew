package com.orders.resturantorder.Base




class AppConstant {
    companion object {

        // SQLite cache constants
        const val DB_NAME = "accounts.db"
        const val TABLE_NAME = "users"

        // preference cache constants
        const val PREFERENCE_NAME = "account_pref"
        const val USER_INFO = "user_login_info"
        const val MOBILE_AUTH_SETTING = "mobile_authentication_setting"
        const val SECURITY_SETTING = "security_setting"
        const val FCM_TOKEN = "fcm_token"
        const val PREF_KEY_ACCESS_TOKEN = "Pref_Key_Access_Token"
        const val PREF_KEY_REFRESH_TOKEN = "Pref_Key_Refresh_Token"
        const val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"

        // debug constants
        const val DEBUG_TAG: String = ("DEBUG :: >>>> ")

        /// API BASE-URL constants
        //const val BASE_URL: String = ("https://stgaccgateway.azurewebsites.net") // staging
        //const val BASE_URL: String = ("https://secure.infotech-accounting.com") // live

        // bundle constants
        const val SERVICE_OPTION = "service_option"
        const val SALES_ITEM = "sales_item"
        const val custom_items = "custom_items"
        const val PURCHASE_ITEM = "purchase_item"
        const val BANK_ACCOUNT = "bank_account"
        const val CONTACT_INFO = "contact_info"
        const val INVENTORY_ITEM = "inventory_item"
        const val EXCEPTION_VIEW = "exception_view"
        const val PURCHASE_RESULT = "purchase_result"
        const val RECONCILIATION_LIST = "reconciliation_list"
        const val ATTACHMENT_RESULT = "attachment_result"
        const val CUSTOM_FIELD_ITEM = "custom_field_item"
        const val CUSTOM_FIELD = "custom_field"
        const val LINE_ITEMS = "line_items"

        var NOTI_OPTION = "approvalNotificationDetails"

        //navigation result constants
        const val RETRY_BTN_KEY = "retry_btn_result"

        //api endpoint constants
        const val API_LOGIN_USER: String = "/authguard/MobileAppLogin"
        const val API_REFRESH_TOKEN: String = "/authguard/MobileAppRefreshToken"
        const val API_BANK_ACCOUNTS: String = "/Bank/GetBankAccbyOrg"
        const val API_LOGOUT: String = "/authguard/revoketoken"
        const val API_DEVICE_REGISTRATION: String = "/authguard/createdeviceregistrationusers"
        const val API_CHANGE_PASSWORD: String = "/authguard/changepassword"
        const val API_BANK_RECONCILIATION: String = "/bank/banksstatementlines/{bankID}"
        const val API_DASHBOARD_PAYABLE_RECEIVABLES: String = "/dashboard/getdashboard"
        const val API_DASHBOARD_CASH_IN_OUT: String = "/dashboard/getdashboardcashinout"
        const val API_ATTACHMENT_FILES: String = "/document/getfiles/{TransID}/{TransTypeID}"
        const val API_DOWNLOAD_ATTACHMENT: String = "/document/DownloadAttachFile"
        const val API_SALES_INVOICE: String = "/salesinvoice/getinvoiceorder"
        const val API_SALES_INVOICE_DETAILS: String = "/salesinvoice/mobilegetcontactsbystatusid"
        const val API_SALES_CREDIT_NOTES_DETAILS: String = "SalesCreditnotes/GetSalesCreditdetailsById"
        const val API_SALES_RECEIVED_MONEY_DETAILS: String = "/ReceiveMoney/GetReceiveMoneyTrans"
        const val API_SALES_QUOTATION: String = "/Sales/getquotesorder"
        const val API_SALES_QUOTATION_DETAILS: String = "/sales/mobilegetcontactsbystatusid"
        const val API_SALES_QUOTATION_APPROVE_DETAILS: String = "/sales/getquotedetailsbyid"
        const val API_SALES_INVOICE_APPROVE_DETAILS: String = "/salesinvoice/getinvoicedetailsbyid"
        const val API_ALL_CONTACTS: String = "/contacts/getallcontacts"
        const val API_SALES_QUOTATION_FINAL_APPROVE: String = "/sales/approvequotationorder/136"
        const val API_SALES_INVOICE_FINAL_APPROVE: String = "/salesinvoice/approveinvoiceorder/103"
        const val API_SALES_CREDIT_NOTE_FINAL_APPROVE: String = "/SalesCreditnotes/SaveUpdateSalesCreditNote/0"
        const val API_SALES_QUOTATION_FINAL_REJECT: String = "/sales/deletequotes/136"
        const val API_SALES_INVOICE_FINAL_REJECT: String = "/salesinvoice/deleteinvoices/24"
        const val API_PURCHASE_ORDER_FINAL_DELETE: String = "/Purchase/DeletePurchaseOrder"
        const val API_PURCHASE_BILL_FINAL_DELETE: String = "/Bills/DeleteBills/175"
        const val API_SALES_INVOICE_FINAL_VOID: String = "/salesinvoice/VoidInvoices/0"
        const val API_SALES_INVOICE_CR_FINAL_VOID: String = "/SalesCreditnotes/VoidSalesCreditnotes/186"
        const val API_PURCHASE_BILL_FINAL_VOID: String = "/Bills/VoidBills/125"
        const val API_PURCHASE_BILLS: String = "/bills/billsearchlist"
        const val API_PURCHASE_BILLS_CONTACTS: String = "/bills/mobilegetcontactsbystatusid"
        const val API_PURCHASE_ORDER_CONTACTS: String = "/Purchase/mobilegetcontactsbystatusid"
        const val API_PURCHASE_ORDERS: String = "/Purchase/getpurchaseorder"
        const val API_PURCHASE_ORDER_DETAIL: String = "/Purchase/getpurchaseorderdetailbyid/{TransID}"
        const val API_PURCHASE_BILL_CREDIT_NOTES_DETAILS: String = "/creditnotes/getvendorcreditdetailsbyid"
        const val API_PURCHASE_BILL_SPENT_MONEY_DETAILS: String = "SpendMoney/GetSpendMoneyTrans"
        const val API_PURCHASE_BILL_APPROVE_DETAILS: String = "/bills/getbilldetailsbyid"
        const val API_PURCHASE_BILL_FINAL_APPROVE: String = "/bills/saveupdatebill/0"
        const val API_PURCHASE_ORDER_FINAL_APPROVE: String = "/Purchase/approvepurchaseorder/127"
        const val API_PURCHASE_BILL_CR_FINAL_APPROVE: String = "/creditnotes/SaveUpdateCreditNote/0"
        const val API_PURCHASE_BILL_CR_FINAL_VOID: String = "/Creditnotes/VoidCreditnotes/183"
        const val API_CONTACTS_DETAIL: String = "/contacts/GetContactDetailByID"
        const val API_CONTACTS_STATEMENT: String = "/accountsreceivable/getviewstatements"
        const val API_CHANGE_ORGANIZATION: String = "/authguard/mobileappchangeOrganization"
        const val API_DASHBOARD_PROFIT_LOSS: String = "/ProfitAndLoss/GetProfitandLossReport"
        const val API_DASHBOARD_BATCH_COUNT: String = "/dashboard/MobileDashboardBadgeCountDetails"
        const val API_UPDATE_PROFILE_PIC: String = "/UserProfile/PostSaveUserDetails"
        const val API_SERVICE_SETTING: String = "/Organization/MobileSettingsUpdate"
        const val API_FORGOT_PASSWORD: String = "/authguard/forgotpassword"
        const val API_DELIVERY_ORDER: String = "/GetDeliveryOrder"
        const val API_DELIVERY_ORDER_DETAIL: String = "/GetDeliveryOrderByStatus/{TransID}"
        const val API_DELIVERY_ORDER_APPROVE_DETAILS: String = "/DeliveryOrderById/{TransID}"
        const val API_DELIVERY_ORDER_FINAL_APPROVE: String = "/ApproveDeliveryOrder/192"
        const val API_DELIVERY_ORDER_FINAL_DELETE: String = "/DeleteDeliveryOrders/192"
        const val API_DELIVERY_ORDER_FINAL_VOID: String = "/ChangeDeliveryOrderStatus/192"
        const val API_CONTACTS_OVERVIEW: String = "/contacts/GetContactOverview"
        const val API_CONTACTS_CASH_IN_OUT: String = "/contacts/GetCashInOutByContactID"
        const val API_CUSTOM_FIELDS: String = "/CustomFieldSetting/GetCustomFieldSettingsByField"
        const val API_INVENTORIES: String = "/Inventory/GetInventories"
        const val API_ACTIVE_INVENTORIES: String = "/Inventory/GetActiveInventoryItems/{ItemID}"
        const val API_INVENTORY_DETAIL: String = "/Inventory/GetInventoryDetailByID"
        const val API_SAVE_INVENTORY: String = "/Inventory/SaveInventoryNotes"
        const val API_CONTACT_GROUPS: String = "/GetContactGroups"
        const val API_COUNTRIES: String = "/GetCountries"
        const val API_BILL_DUE_DATE: String = "/GetDueType"
        const val API_getcontactdetailbyid: String = "/contacts/GetContactDetailByID"
        const val API_INVOICE_DUE_DATE: String = "/GetInvoiceDueType"
        const val API_TAX_TYPE: String = "/GetTaxType"
        const val API_CURRENCIES: String = "/GetInUseCurrencies"
        const val API_PURCHASE_THEME: String = "/GetTemplateThemeList/PO"
        const val API_INVOICE_THEME: String = "/GetTemplateThemeList/Invoice"
        const val API_DEFAULT_PURCHASE_TAX: String = "/GetPOTaxRate"
        const val API_DEFAULT_SALES_TAX: String = "/GetSalesTaxRate"
        const val API_PURCHASE_ACCOUNT: String = "/GetPurchaseChartOfAccounts"
        const val API_SALES_ACCOUNT: String = "/GetSalesChartOfAccounts"
        const val API_UPDATE_CONTACT: String = "/contacts/SaveUpdateContacts"
        const val API_GET_ITEM_TYPE: String = "/GetItemType"
        const val API_GET_PRODUCT_GROUP: String = "/GetProductGroup"
        const val API_UNIT_MEASUREMENT: String = "/GetItemUnitType"
        const val API_UPDATE_ITEMS: String = "/Inventory/SaveUpdateInventory"
        const val API_GET_CHART_ACCOUNT: String = "/Inventory/GetChartOfAccounts"
        const val API_GET_LATEST_APP_VERSION: String = "/AppVersion/GetLatestAppVersion?Platform=android"
        const val API_GET_QUOTE_THEME: String = "/GetTemplateThemeList/{PageID}"
        const val API_SAVE_UPDATE_QUOTE_ORDER: String = "/SalesQuotation/SaveUpdateQuotationOrder/137"
        const val API_TAX_COMPONENTS: String = "/GetTaxCompoundRate"
        const val API_GENERATE_QNO: String = "/SalesQuotation/GenerateQNO"
        const val API_GET_NOTIFICATIONS: String = "/Notification/GetUserNotifications "

        //api param's key constants
        const val API_TOKEN_KEY: String = "Authorization"
        const val REFRESH_TOKEN_KEY: String = "refreshToken"
        const val CUSTOMER_CODE_KEY: String = "custCode"
        const val CENTRAL_ORGANIZATION_ID_KEY: String = "centralOrgID"
        const val CENTRAL_USER_ID_KEY: String = "centralUserID"
        const val BROWSER_UUID_ID_KEY: String = "browseruuid"
        const val PASSWORD_KEY: String = "Password"
        const val CURRENT_PASSWORD_KEY: String = "CurrentPassword"
        const val DEVICE_TYPE_KEY: String = "device"
        const val FROM_DATE_KEY: String = "FromDate"
        const val TO_DATE_KEY: String = "ToDate"
        const val PAGE_ID_KEY: String = "PageID"
        const val FILE_NAME_KEY: String = "FileName"
        const val FILE_PATH_KEY: String = "FilePath"
        const val FIELD_ID_KEY: String = "fieldID"
        const val contactid: String = "contactID"
        const val TRANS_ID_KEY: String = "TransID"
        const val TRANS_TYPE_ID_KEY: String = "TransTypeID"
        const val TRANS_ID_LOWER_KEY: String = "transId"
        const val ITEM_ID_KEY: String = "ItemID"
        const val TRANS_TYPE_ID_LOWER_KEY: String = "transTypeId"
        const val TRANSACTION_TYPE_ID_KEY: String = "TransactionTypeID"
        const val CR_TRANSACTION_TYPE_ID_KEY: String = "CreditNoteTransaction"
        const val ACCOUNT_ID_KEY: String = "bankID"
        const val STATUS_ID_KEY: String = "statusID"
        const val CONTACT_ID_KEY: String = "ContactID"
        const val OLD_INVOICE_ORDER_KEY: String = "OldIO"
        const val NEW_INVOICE_ORDER_KEY: String = "newIO"
        const val OLD_QUOTATION_ORDER_KEY: String = "OldQO"
        const val NEW_QUOTATION_ORDER_KEY: String = "NewQO"
        const val NEW_QUOTE_ORDER_KEY: String = "newQO"
        const val OLD_QUOTE_ORDER_KEY: String = "oldQO"
        const val OLD_BILL_KEY: String = "oldBill"
        const val NEW_BILL_KEY: String = "newBill"
        const val OLD_PO_KEY: String = "oldPO"
        const val NEW_PO_KEY: String = "newPO"
        const val OLD_DO_KEY: String = "oldDo"
        const val NEW_DO_KEY: String = "newDo"
        const val OLD_CREDIT_NOTE_KEY: String = "oldSCN"
        const val NEW_CREDIT_NOTE_KEY: String = "newSCN"
        const val OLD_PURCHASE_CREDIT_NOTE_KEY: String = "oldCN"
        const val NEW_PURCHASE_CREDIT_NOTE_KEY: String = "newCN"
        const val QUOTATION_TRANSACTION_KEY: String = "QuoteTransction"
        const val INVOICE_TRANSACTION_KEY: String = "InvoiceTransction"
        const val INVOICE_CREDIT_TRANSACTION_KEY: String = "SalesCreditNoteTransaction"
        const val BILL_TRANSACTION_KEY: String = "BillTransction"
        const val DELIVERY_TRANSACTION_KEY: String = "DeliveryTransction"
        const val USER_NAME_KEY: String = "UserName"
        const val ACTION_ID_KEY: String = "ActionID"
        const val DELIVERY_ORDER_KEY: String = "DO"
        const val OPTION_KEY: String = "Option"

        //internal storage constants
        var DIRECTORY_NAME: String? = "attachments"
        var PROFILE_IMG_NAME: String? = "avathar"
        var PROFILE_IMG_EXT: String? = ".png"

        //other constants
        const val SPLASH_TIME_OUT: Long = 3000
        const val EMPTY: String = ""
        const val SPACE: String = " "
        const val NOTHING: String = "Nothing"
        const val SERVER_DATE_FORMAT: String = "yyyy-MM-dd'T'HH:mm:ss"
        const val MOBILE_DATE_FORMAT: String = "dd MMM yyyy"
        const val MOBILE_DATE_TIME_FORMAT: String = "dd MMM yy, hh:mm"
        const val SERVER_DEFAULT_DATE: String = "1900-01-01T00:00:00"
        const val BASE64_IMG_TYPE: String = "data:image/png;base64,"
        const val MOBILE: String = "mobile"
        const val BEARER_KEY: String = "Bearer"
        const val IS_AUTHORIZABLE: String = "isAuthorizable: false"
        const val BROWSER_UUID: String = "dev"
        const val DEBIT: String = "DEBIT"
        const val CREDIT: String = "CREDIT"
        const val NOTIFICATION_ID = 0
        const val IMAGE_RESULT_CODE = 122
        const val MOBILE_DATE_FORMAT_OPTIONAL: String = "yyyy-MM-dd"
        const val REQUEST_IMAGE_CAPTURE = 1
        const val FRAGMENT_SAVED_STATE = "RESULT_FROM_FRAGMENT"


        //access rights constants

        //dashboard -access
        const val DASHBOARD_ACCESS: String = "Dashboard"
        const val RECONCILIATION_ACCESS: String = "Reconcilation"
        const val BANK_STATEMENT_ACCESS: String = "Bank Statements"
        const val BANK_ACCOUNT_ACCESS: String = "Banking Accounts"
        const val BANK_ACCESS: String = "Bank"

        //contact -access
        const val CONTACTS_ACCESS: String = "Contacts"

        //sales - access
        const val SALES_ACCESS: String = "Sales"
        const val INVOICE_ACCESS: String = "Invoice"
        const val QUOTATION_ACCESS: String = "Quotation"
        const val SALES_CREDIT_NOTE_ACCESS: String = "SalesCreditNotes"
        const val RECEIVED_MONEY_ACCESS: String = "Receive Money"

        //delivery order - access
        const val DO_ACCESS: String = "Delivery Order"
        const val DELIVERY_ORDER_ACCESS: String = "Delivery"

        //purchase - access
        const val PURCHASE_ACCESS: String = "Purchase"
        const val BILL_ACCESS: String = "Bills"
        const val PO_ACCESS: String = "Purchase order"
        const val CREDIT_NOTE_ACCESS: String = "Credit Notes"
        const val SPENT_MONEY_ACCESS: String =  "Spend Money"

        //service - access
        const val SETTING_ACCESS: String = "Settings"
        const val INVENTORY_ACCESS: String = "Inventory"  //Inventory
        const val INVENTORY_ITEMS_ACCESS: String = "Items" //Inventory

        //map key constant
        const val HASH_STATUS: String = "status"
       const val CAAccountName: String = "AccountName"
        const val HASH_TOTAL_AMT: String = "totalAmount"
        const val HASH_SUBTOTAL_AMT: String = "subtotalAmount"
        const val HASH_CONTACT_NAME: String = "contactName"
        const val HASH_DATE: String = "date"
        const val HASH_DUE_DATE: String = "dueDate"
        const val HASH_DUE_AMOUNT: String = "dueAmount"
        const val HASH_DUE_AMOUNT_LABEL: String = "dueAmountLabel"
        const val HASH_INVOICE_NO: String = "invoiceNo"
        const val HASH_TOTAL_TAX: String = "totalTaxAmount"
        const val HASH_APPROVE_BUTTON: String = "approveButton"
        const val HASH_APPBAR: String = "appBar"
        const val HASH_DUE_DATE_VISIBILITY: String = "due_date_visibility"
        const val HASH_DATE_VISIBILITY: String = "date_visibility"
        const val HASH_DO_ATTENDEE: String = "deliveryAttendee"
        const val HASH_DO_POSTAL_ATTENDEE: String = "postalAttendee"
        const val HASH_DO_CONTACT_NO: String = "deliveryContactNumber"
        const val HASH_DO_INSTRUCTION: String = "deliveryInstruction"
        const val HASH_DO_ADDRESS: String = "deliveryAddress"
        const val HASH_DO_POSTAL_ADDRESS: String = "postalAddress"

        enum class BiometricType {
            FINGERPRINT, FACE, IRIS, NONE
        }

        //currency type constants
        val CURRENCIES: Array<Triple<String, String, String>> = arrayOf(
            Triple("EUR", "Euro", "€"),
            Triple("USD", "United States Dollar", "$"),
            Triple("GBP", "British Pound", "£"),
            Triple("CZK", "Czech Koruna", "Kč"),
            Triple("TRY", "Turkish Lira", "₺"),
            Triple("AED", "Emirati Dirham", "د.إ"),
            Triple("AFN", "Afghanistan Afghani", "؋"),
            Triple("ARS", "Argentine Peso", "$"),
            Triple("AUD", "Australian Dollar", "$"),
            Triple("BBD", "Barbados Dollar", "$"),
            Triple("BDT", "Bangladeshi Taka", " Tk"),
            Triple("BGN", "Bulgarian Lev", "лв"),
            Triple("BHD", "Bahraini Dinar", "BD"),
            Triple("BMD", "Bermuda Dollar", "$"),
            Triple("BND", "Brunei Darussalam Dollar", "$"),
            Triple("BOB", "Bolivia Bolíviano", "\$b"),
            Triple("BRL", "Brazil Real", "R$"),
            Triple("BTN", "Bhutanese Ngultrum", "Nu."),
            Triple("BZD", "Belize Dollar", "BZ$"),
            Triple("CAD", "Canada Dollar", "$"),
            Triple("CHF", "Switzerland Franc", "CHF"),
            Triple("CLP", "Chile Peso", "$"),
            Triple("CNY", "China Yuan Renminbi", "¥"),
            Triple("COP", "Colombia Peso", "$"),
            Triple("CRC", "Costa Rica Colon", "₡"),
            Triple("DKK", "Denmark Krone", "kr"),
            Triple("DOP", "Dominican Republic Peso", "RD$"),
            Triple("EGP", "Egypt Pound", "£"),
            Triple("ETB", "Ethiopian Birr", "Br"),
            Triple("GEL", "Georgian Lari", "₾"),
            Triple("GHS", "Ghana Cedi", "¢"),
            Triple("GMD", "Gambian dalasi", "D"),
            Triple("GYD", "Guyana Dollar", "$"),
            Triple("HKD", "Hong Kong Dollar", "$"),
            Triple("HRK", "Croatia Kuna", "kn"),
            Triple("HUF", "Hungary Forint", "Ft"),
            Triple("IDR", "Indonesia Rupiah", "Rp"),
            Triple("ILS", "Israel Shekel", "₪"),
            Triple("INR", "Indian Rupee", "₹"),
            Triple("ISK", "Iceland Krona", "kr"),
            Triple("JMD", "Jamaica Dollar", "J$"),
            Triple("JPY", "Japanese Yen", "¥"),
            Triple("KES", "Kenyan Shilling", "KSh"),
            Triple("KRW", "Korea (South) Won", "₩"),
            Triple("KWD", "Kuwaiti Dinar", "د.ك"),
            Triple("KYD", "Cayman Islands Dollar", "$"),
            Triple("KZT", "Kazakhstan Tenge", "лв"),
            Triple("LAK", "Laos Kip", "₭"),
            Triple("LKR", "Sri Lanka Rupee", "₨"),
            Triple("LRD", "Liberia Dollar", "$"),
            Triple("LTL", "Lithuanian Litas", "Lt"),
            Triple("MAD", "Moroccan Dirham", "MAD"),
            Triple("MDL", "Moldovan Leu", "MDL"),
            Triple("MKD", "Macedonia Denar", "ден"),
            Triple("MNT", "Mongolia Tughrik", "₮"),
            Triple("MUR", "Mauritius Rupee", "₨"),
            Triple("MWK", "Malawian Kwacha", "MK"),
            Triple("MXN", "Mexico Peso", "$"),
            Triple("MYR", "Malaysia Ringgit", "RM"),
            Triple("MZN", "Mozambique Metical", "MT"),
            Triple("NAD", "Namibia Dollar", "$"),
            Triple("NGN", "Nigeria Naira", "₦"),
            Triple("NIO", "Nicaragua Cordoba", "C$"),
            Triple("NOK", "Norway Krone", "kr"),
            Triple("NPR", "Nepal Rupee", "₨"),
            Triple("NZD", "New Zealand Dollar", "$"),
            Triple("OMR", "Oman Rial", "﷼"),
            Triple("PEN", "Peru Sol", "S/."),
            Triple("PGK", "Papua New Guinean Kina", "K"),
            Triple("PHP", "Philippines Peso", "₱"),
            Triple("PKR", "Pakistan Rupee", "₨"),
            Triple("PLN", "Poland Zloty", "zł"),
            Triple("PYG", "Paraguay Guarani", "Gs"),
            Triple("QAR", "Qatar Riyal", "﷼"),
            Triple("RON", "Romania Leu", "lei"),
            Triple("RSD", "Serbia Dinar", "Дин."),
            Triple("RUB", "Russia Ruble", "₽"),
            Triple("SAR", "Saudi Arabia Riyal", "﷼"),
            Triple("SEK", "Sweden Krona", "kr"),
            Triple("SGD", "Singapore Dollar", "$"),
            Triple("SOS", "Somalia Shilling", "S"),
            Triple("SRD", "Suriname Dollar", "$"),
            Triple("THB", "Thailand Baht", "฿"),
            Triple("TTD", "Trinidad and Tobago Dollar", "TT$"),
            Triple("TWD", "Taiwan New Dollar", "NT$"),
            Triple("TZS", "Tanzanian Shilling", "TSh"),
            Triple("UAH", "Ukraine Hryvnia", "₴"),
            Triple("UGX", "Ugandan Shilling", "USh"),
            Triple("UYU", "Uruguay Peso", "\$U"),
            Triple("VEF", "Venezuela Bolívar", "Bs"),
            Triple("VND", "Viet Nam Dong", "₫"),
            Triple("YER", "Yemen Rial", "﷼"),
            Triple("ZAR", "South Africa Rand", "R")
        )
    }
}