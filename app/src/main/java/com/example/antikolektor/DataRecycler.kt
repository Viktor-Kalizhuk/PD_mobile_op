package com.example.antikolektor

import android.graphics.Bitmap


data class DataRecycler(
    var id: Int,
    var type: String,
    var phone: String,
    var subtype: String,
    var comment: String,
    var position: Int
)

data class DataListHist(
    val data: List<DataHist>
)

data class DataHist(
    val id: Int, val paid: String, val amount: String, val payed_at: String?, val refund: String
)

data class DataListStages(
    var data: List<DataStages>
)

data class DataStages(
    var id: Int,
    var stage_id: Int,
    var sort: Int,
    var title: String,
    var documents: List<String>,
    var active: String,
    var update_date: String,
    var active_date: String?
)

data class DataListNotif(
    val data: List<DataNotif>
)

data class DataNotif(
    var id: String,
    var type: String,
    var message: String,
    var date: String,

    )

data class readNotification(
    val id: String
)

data class DataNotifCallback(
    var id: String, var type: String, var message: String, var date: String, var position: Int
)

data class IdCredit(
    val id: Int?
)

data class Profile(
    var address: String?,
    var gender: String?,
    var birthday: String?,
    val actual_address: String?,
    val reason_for_delays_text: String?,
    val reason_for_delays: String?,
    val nature_of_work: String?,
    val additional_income: String?,
    val additional_income_amount: String?,
    val cost: String?,
    val cost_amount: String?,
    val total_bank: String?,
    val total_debt: String?,
    val monthly_payment: String?,
    val total_income: String?,
    val agreement: String?,
    val passport_numb: String?,
    val passport_series: String?,
    val passport_issued_by: String?,
    val passport_date_of_issue: String?,
    val passport_department_code: String?,
    val place_of_birth: String?
)

data class PhoneProfile(
    val phone: String?
)

data class ChangeCode(
    val code: String?
)

data class PhoneProfilePoz(
    val phone: String?, val position: Int
)

data class Credit(
    val bank: List<Bank?>, val mfo: List<String?>
)

data class Bank(
    val id: Int,
    val user_id: Int,
    val bank_id: Int,
    val title: String?,
    val numb: String?,
    val type: String?,
    val agreement_date: String?,
    val amount_of_credit: String?,
    val bank_type: String?,
    val mfo_type: String?,
    val balance_owed: String?,
    val monthly_payment: String?,
    val guarantor: String?,
    val pledge: String?,
    val judgment: String?,
    val documents_you_have: String?,
    val wishes_problems: String?,
    val debt_structure: String?,
    val created_at: String?,
    val updated_at: String?,
    val files: List<Files>
) : java.io.Serializable

data class Files(
    val id: Int?,
    val name: String?,
    val original_name: String?,
    val type: String?,
    val size: String?,
    val path: String?,
    val sort: Int?,
    val user_id: Int?,
    val file_type_id: Int?,
    val stage_id: Int?,
    val credit_id: Int?,
    val sender_id: Int?,
    val created_at: String?,
    val updated_at: String?
) : java.io.Serializable

data class DataProfile(
    val id: Int,
    var first_name: String?,
    var middle_name: String?,
    var last_name: String?,
    var email: String?,
    val phone: List<PhoneProfile>,
    val profile: List<Profile>,
    val program: Boolean?,
    val credit: Credit
)

data class DataUserPanel(
    val first_name: String?,
    val middle_name: String?,
    val last_name: String?,
    val photo: String?,
    val phone: String?,
    val roleText: String?,
    val message: String?
)

data class DataListUserPanel(
    val data: DataUserPanel
)

data class DataListDirectory(
    val suggestions: List<DataDirectory>
)

data class DataDirectory(
    val value: String?, val unrestricted_value: String?, val data: DataData
)

data class Query(
    val query: String
)

data class putProfile(
    val first_name: String,
    val last_name: String?,
    val middle_name: String?,
    val email: String?,
    val address: String?,
    val birthday: String?,
    val gender: String?
)

data class putProfileStepTwo(
    val total_income: String,
    val additional_income_amount: String,
    val cost_amount: String,
    val monthly_payment: String,
    val reason_for_delays: String,
    val nature_of_work: String,
    val additional_income: String,
    val cost: String
)

data class DataData(
    val postal_code: String?,
    val country: String?,
    val country_iso_code: String?,
    val federal_district: String?,
    val region_fias_id: String?,
    val region_kladr_id: String?,
    val region_iso_code: String?,
    val region_with_type: String?,
    val region_type: String?,
    val region_type_full: String?,
    val region: String?,
    val area_fias_id: String?,
    val area_kladr_id: String?,
    val area_with_type: String?,
    val area_type: String?,
    val area_type_full: String?,
    val area: String?,
    val city_fias_id: String?,
    val city_kladr_id: String?,
    val city_with_type: String?,
    val city_type: String?,
    val city_type_full: String?,
    val city: String?,
    val city_area: String?,
    val city_district_fias_id: String?,
    val city_district_kladr_id: String?,
    val city_district_with_type: String?,
    val city_district_type: String?,
    val city_district_type_full: String?,
    val city_district: String?,
    val settlement_fias_id: String?,
    val settlement_kladr_id: String?,
    val settlement_with_type: String?,
    val settlement_type: String?,
    val settlement_type_full: String?,
    val settlement: String?,
    val street_fias_id: String?,
    val street_kladr_id: String?,
    val street_with_type: String?,
    val street_type: String?,
    val street_type_full: String?,
    val street: String?,
    val stead_fias_id: String?,
    val stead_cadnum: String?,
    val stead_type: String?,
    val stead_type_full: String?,
    val stead: String?,
    val house_fias_id: String?,
    val house_kladr_id: String?,
    val house_cadnum: String?,
    val house_type: String?,
    val house_type_full: String?,
    val house: String?,
    val block_type: String?,
    val block_type_full: String?,
    val block: String?,
    val entrance: String?,
    val floor: String?,
    val flat_fias_id: String?,
    val flat_cadnum: String?,
    val flat_type: String?,
    val flat_type_full: String?,
    val flat: String?,
    val flat_area: String?,
    val square_meter_price: String?,
    val flat_price: String?,
    val room_fias_id: String?,
    val room_cadnum: String?,
    val room_type: String?,
    val room_type_full: String?,
    val room: String?,
    val postal_box: String?,
    val fias_id: String?,
    val fias_code: String?,
    val fias_level: String?,
    val fias_actuality_state: String?,
    val kladr_id: String?,
    val geoname_id: String?,
    val capital_marker: String?,
    val okato: String?,
    val oktmo: String?,
    val tax_office: String?,
    val tax_office_legal: String?,
    val timezone: String?,
    val geo_lat: String?,
    val geo_lon: String?,
    val beltway_hit: String?,
    val beltway_distance: String?,
    val metro: String?,
    val divisions: String?,
    val qc_geo: String?,
    val qc_complete: String?,
    val qc_house: String?,
    val history_values: List<String>?,
    val unparsed_parts: String?,
    val source: String?,
    val qc: String?
)

data class IdRemittance(
    val id: String
)


data class MyDocumentDataStruct(
    val structure: List<DataDoc>, val documents: List<DocumentsStr>

)

data class DataSerch(
    val data: List<Serch>

)

data class Serch(
    val id: Int, val title: String
)

data class DocumentsStr(
    val id: Int?,
    val name: String?,
    val original_name: String?,
    val type: String?,
    val size: String?,
    val path: String?,
    val sort: Int?,
    val user_id: Int?,
    val file_type_id: Int?,
    val stage_id: Int?,
    val credit_id: Int?,
    val sender_id: Int?,
    val created_at: String?,
    val updated_at: String?,
)

data class DocumentsStrr(
    val id: Int?,
    val name: String?,
    val original_name: String?,
    val type: String?,
    val size: String?,
    val path: String?,
    val sort: Int?,
    val user_id: Int?,
    val file_type_id: Int?,
    val stage_id: Int?,
    val credit_id: Int?,
    val sender_id: Int?,
    val created_at: String?,
    val updated_at: String?,
    val bitmap: Bitmap?

)


data class DataFile(
    val name: String?,
    val type: String?,
    val size: String?,
    val path: String?,
    val user_id: Int?,
    val file_type_id: Int?,
    val sender_id: Int?,
    val created_at: String?,
    val updated_at: String?,
    val id: Int?,
)


data class DocumentsStrPoz(
    val id: Int?,
    val pos: Int,
    val action: Boolean,
    val path: String?,
    val sender_id: Int?,
    val user_id: Int?,
    val bitmap: Bitmap?

)

data class DataDoc(
    val id: Int,
    val title: String,
    val category: String,
    val sort: Int,
    val created_at: String,
    val updated_at: String
)

data class CreditData(
    val title: String,
    val bank_id: String,
    val balance_owed: String,
    val mounthly_payment: String,
    val documents_you_have: String
)

data class SharedDataDocument(
    val id: Int?,
    val name: String?,
    val original_name: String?,
    val type: String?,
    val size: String?,
    val path: String?,
    val sort: Int?,
    val user_id: Int?,
    val file_type_id: Int?,
    val stage_id: Int?,
    val credit_id: Int?,
    val sender_id: Int?,
    val created_at: String?,
    val updated_at: String?,
    val formated_date: String?

)