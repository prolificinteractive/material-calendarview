package com.jalalicalendarutil;

import java.util.TimeZone;

/**
 * This is simply all the available TimeZones from java.util.TimeZone as type
 * safe enum
 */
public enum TimeZones {
	DEFAULT(TimeZone.getDefault()),
	// default JDK 1.4.2 time zones
	ACT(TimeZone.getTimeZone("ACT")), AET(TimeZone.getTimeZone("AET")), AFRICA_ABIDJAN(TimeZone.getTimeZone("Africa/Abidjan")), AFRICA_ACCRA(TimeZone.getTimeZone("Africa/Accra")), AFRICA_ADDIS_ABABA(TimeZone
			.getTimeZone("Africa/Addis_Ababa")), AFRICA_ALGIERS(TimeZone.getTimeZone("Africa/Algiers")), AFRICA_ASMERA(TimeZone.getTimeZone("Africa/Asmera")), AFRICA_BAMAKO(TimeZone.getTimeZone("Africa/Bamako")), AFRICA_BANGUI(TimeZone
			.getTimeZone("Africa/Bangui")), AFRICA_BANJUL(TimeZone.getTimeZone("Africa/Banjul")), AFRICA_BISSAU(TimeZone.getTimeZone("Africa/Bissau")), AFRICA_BLANTYRE(TimeZone.getTimeZone("Africa/Blantyre")), AFRICA_BRAZZAVILLE(TimeZone
			.getTimeZone("Africa/Brazzaville")), AFRICA_BUJUMBURA(TimeZone.getTimeZone("Africa/Bujumbura")), AFRICA_CAIRO(TimeZone.getTimeZone("Africa/Cairo")), AFRICA_CASABLANCA(TimeZone.getTimeZone("Africa/Casablanca")), AFRICA_CEUTA(
			TimeZone.getTimeZone("Africa/Ceuta")), AFRICA_CONAKRY(TimeZone.getTimeZone("Africa/Conakry")), AFRICA_DAKAR(TimeZone.getTimeZone("Africa/Dakar")), AFRICA_DAR_ES_SALAAM(TimeZone.getTimeZone("Africa/Dar_es_Salaam")), AFRICA_DJIBOUTI(
			TimeZone.getTimeZone("Africa/Djibouti")), AFRICA_DOUALA(TimeZone.getTimeZone("Africa/Douala")), AFRICA_EL_AAIUN(TimeZone.getTimeZone("Africa/El_Aaiun")), AFRICA_FREETOWN(TimeZone.getTimeZone("Africa/Freetown")), AFRICA_GABORONE(
			TimeZone.getTimeZone("Africa/Gaborone")), AFRICA_HARARE(TimeZone.getTimeZone("Africa/Harare")), AFRICA_JOHANNESBURG(TimeZone.getTimeZone("Africa/Johannesburg")), AFRICA_KAMPALA(TimeZone.getTimeZone("Africa/Kampala")), AFRICA_KHARTOUM(
			TimeZone.getTimeZone("Africa/Khartoum")), AFRICA_KIGALI(TimeZone.getTimeZone("Africa/Kigali")), AFRICA_KINSHASA(TimeZone.getTimeZone("Africa/Kinshasa")), AFRICA_LAGOS(TimeZone.getTimeZone("Africa/Lagos")), AFRICA_LIBREVILLE(
			TimeZone.getTimeZone("Africa/Libreville")), AFRICA_LOME(TimeZone.getTimeZone("Africa/Lome")), AFRICA_LUANDA(TimeZone.getTimeZone("Africa/Luanda")), AFRICA_LUBUMBASHI(TimeZone.getTimeZone("Africa/Lubumbashi")), AFRICA_LUSAKA(
			TimeZone.getTimeZone("Africa/Lusaka")), AFRICA_MALABO(TimeZone.getTimeZone("Africa/Malabo")), AFRICA_MAPUTO(TimeZone.getTimeZone("Africa/Maputo")), AFRICA_MASERU(TimeZone.getTimeZone("Africa/Maseru")), AFRICA_MBABANE(TimeZone
			.getTimeZone("Africa/Mbabane")), AFRICA_MOGADISHU(TimeZone.getTimeZone("Africa/Mogadishu")), AFRICA_MONROVIA(TimeZone.getTimeZone("Africa/Monrovia")), AFRICA_NAIROBI(TimeZone.getTimeZone("Africa/Nairobi")), AFRICA_NDJAMENA(
			TimeZone.getTimeZone("Africa/Ndjamena")), AFRICA_NIAMEY(TimeZone.getTimeZone("Africa/Niamey")), AFRICA_NOUAKCHOTT(TimeZone.getTimeZone("Africa/Nouakchott")), AFRICA_OUAGADOUGOU(TimeZone.getTimeZone("Africa/Ouagadougou")), AFRICA_PORTO_NOVO(
			TimeZone.getTimeZone("Africa/Porto-Novo")), AFRICA_SAO_TOME(TimeZone.getTimeZone("Africa/Sao_Tome")), AFRICA_TIMBUKTU(TimeZone.getTimeZone("Africa/Timbuktu")), AFRICA_TRIPOLI(TimeZone.getTimeZone("Africa/Tripoli")), AFRICA_TUNIS(
			TimeZone.getTimeZone("Africa/Tunis")), AFRICA_WINDHOEK(TimeZone.getTimeZone("Africa/Windhoek")), AGT(TimeZone.getTimeZone("AGT")), AMERICA_ADAK(TimeZone.getTimeZone("America/Adak")), AMERICA_ANCHORAGE(TimeZone
			.getTimeZone("America/Anchorage")), AMERICA_ANGUILLA(TimeZone.getTimeZone("America/Anguilla")), AMERICA_ANTIGUA(TimeZone.getTimeZone("America/Antigua")), AMERICA_ARAGUAINA(TimeZone.getTimeZone("America/Araguaina")), AMERICA_ARUBA(
			TimeZone.getTimeZone("America/Aruba")), AMERICA_ASUNCION(TimeZone.getTimeZone("America/Asuncion")), AMERICA_ATKA(TimeZone.getTimeZone("America/Atka")), AMERICA_BARBADOS(TimeZone.getTimeZone("America/Barbados")), AMERICA_BELEM(
			TimeZone.getTimeZone("America/Belem")), AMERICA_BELIZE(TimeZone.getTimeZone("America/Belize")), AMERICA_BOA_VISTA(TimeZone.getTimeZone("America/Boa_Vista")), AMERICA_BOGOTA(TimeZone.getTimeZone("America/Bogota")), AMERICA_BOISE(
			TimeZone.getTimeZone("America/Boise")), AMERICA_BUENOS_AIRES(TimeZone.getTimeZone("America/Buenos_Aires")), AMERICA_CAMBRIDGE_BAY(TimeZone.getTimeZone("America/Cambridge_Bay")), AMERICA_CANCUN(TimeZone
			.getTimeZone("America/Cancun")), AMERICA_CARACAS(TimeZone.getTimeZone("America/Caracas")), AMERICA_CATAMARCA(TimeZone.getTimeZone("America/Catamarca")), AMERICA_CAYENNE(TimeZone.getTimeZone("America/Cayenne")), AMERICA_CAYMAN(
			TimeZone.getTimeZone("America/Cayman")), AMERICA_CHICAGO(TimeZone.getTimeZone("America/Chicago")), AMERICA_CHIHUAHUA(TimeZone.getTimeZone("America/Chihuahua")), AMERICA_CORDOBA(TimeZone.getTimeZone("America/Cordoba")), AMERICA_COSTA_RICA(
			TimeZone.getTimeZone("America/Costa_Rica")), AMERICA_CUIABA(TimeZone.getTimeZone("America/Cuiaba")), AMERICA_CURACAO(TimeZone.getTimeZone("America/Curacao")), AMERICA_DANMARKSHAVN(TimeZone.getTimeZone("America/Danmarkshavn")), AMERICA_DAWSON(
			TimeZone.getTimeZone("America/Dawson")), AMERICA_DAWSON_CREEK(TimeZone.getTimeZone("America/Dawson_Creek")), AMERICA_DENVER(TimeZone.getTimeZone("America/Denver")), AMERICA_DETROIT(TimeZone.getTimeZone("America/Detroit")), AMERICA_DOMINICA(
			TimeZone.getTimeZone("America/Dominica")), AMERICA_EDMONTON(TimeZone.getTimeZone("America/Edmonton")), AMERICA_EIRUNEPE(TimeZone.getTimeZone("America/Eirunepe")), AMERICA_EL_SALVADOR(TimeZone.getTimeZone("America/El_Salvador")), AMERICA_ENSENADA(
			TimeZone.getTimeZone("America/Ensenada")), AMERICA_FORT_WAYNE(TimeZone.getTimeZone("America/Fort_Wayne")), AMERICA_FORTALEZA(TimeZone.getTimeZone("America/Fortaleza")), AMERICA_GLACE_BAY(TimeZone
			.getTimeZone("America/Glace_Bay")), AMERICA_GODTHAB(TimeZone.getTimeZone("America/Godthab")), AMERICA_GOOSE_BAY(TimeZone.getTimeZone("America/Goose_Bay")), AMERICA_GRAND_TURK(TimeZone.getTimeZone("America/Grand_Turk")), AMERICA_GRENADA(
			TimeZone.getTimeZone("America/Grenada")), AMERICA_GUADELOUPE(TimeZone.getTimeZone("America/Guadeloupe")), AMERICA_GUATEMALA(TimeZone.getTimeZone("America/Guatemala")), AMERICA_GUAYAQUIL(TimeZone.getTimeZone("America/Guayaquil")), AMERICA_GUYANA(
			TimeZone.getTimeZone("America/Guyana")), AMERICA_HALIFAX(TimeZone.getTimeZone("America/Halifax")), AMERICA_HAVANA(TimeZone.getTimeZone("America/Havana")), AMERICA_HERMOSILLO(TimeZone.getTimeZone("America/Hermosillo")), AMERICA_INDIANA_INDIANAPOLIS(
			TimeZone.getTimeZone("America/Indiana/Indianapolis")), AMERICA_INDIANA_KNOX(TimeZone.getTimeZone("America/Indiana/Knox")), AMERICA_INDIANA_MARENGO(TimeZone.getTimeZone("America/Indiana/Marengo")), AMERICA_INDIANA_VEVAY(TimeZone
			.getTimeZone("America/Indiana/Vevay")), AMERICA_INDIANAPOLIS(TimeZone.getTimeZone("America/Indianapolis")), AMERICA_INUVIK(TimeZone.getTimeZone("America/Inuvik")), AMERICA_IQALUIT(TimeZone.getTimeZone("America/Iqaluit")), AMERICA_JAMAICA(
			TimeZone.getTimeZone("America/Jamaica")), AMERICA_JUJUY(TimeZone.getTimeZone("America/Jujuy")), AMERICA_JUNEAU(TimeZone.getTimeZone("America/Juneau")), AMERICA_KENTUCKY_LOUISVILLE(TimeZone
			.getTimeZone("America/Kentucky/Louisville")), AMERICA_KENTUCKY_MONTICELLO(TimeZone.getTimeZone("America/Kentucky/Monticello")), AMERICA_KNOX_IN(TimeZone.getTimeZone("America/Knox_IN")), AMERICA_LA_PAZ(TimeZone
			.getTimeZone("America/La_Paz")), AMERICA_LIMA(TimeZone.getTimeZone("America/Lima")), AMERICA_LOS_ANGELES(TimeZone.getTimeZone("America/Los_Angeles")), AMERICA_LOUISVILLE(TimeZone.getTimeZone("America/Louisville")), AMERICA_MACEIO(
			TimeZone.getTimeZone("America/Maceio")), AMERICA_MANAGUA(TimeZone.getTimeZone("America/Managua")), AMERICA_MANAUS(TimeZone.getTimeZone("America/Manaus")), AMERICA_MARTINIQUE(TimeZone.getTimeZone("America/Martinique")), AMERICA_MAZATLAN(
			TimeZone.getTimeZone("America/Mazatlan")), AMERICA_MENDOZA(TimeZone.getTimeZone("America/Mendoza")), AMERICA_MENOMINEE(TimeZone.getTimeZone("America/Menominee")), AMERICA_MERIDA(TimeZone.getTimeZone("America/Merida")), AMERICA_MEXICO_CITY(
			TimeZone.getTimeZone("America/Mexico_City")), AMERICA_MIQUELON(TimeZone.getTimeZone("America/Miquelon")), AMERICA_MONTERREY(TimeZone.getTimeZone("America/Monterrey")), AMERICA_MONTEVIDEO(TimeZone
			.getTimeZone("America/Montevideo")), AMERICA_MONTREAL(TimeZone.getTimeZone("America/Montreal")), AMERICA_MONTSERRAT(TimeZone.getTimeZone("America/Montserrat")), AMERICA_NASSAU(TimeZone.getTimeZone("America/Nassau")), AMERICA_NEW_YORK(
			TimeZone.getTimeZone("America/New_York")), AMERICA_NIPIGON(TimeZone.getTimeZone("America/Nipigon")), AMERICA_NOME(TimeZone.getTimeZone("America/Nome")), AMERICA_NORONHA(TimeZone.getTimeZone("America/Noronha")), AMERICA_NORTH_DAKOTA_CENTER(
			TimeZone.getTimeZone("America/North_Dakota/Center")), AMERICA_PANAMA(TimeZone.getTimeZone("America/Panama")), AMERICA_PANGNIRTUNG(TimeZone.getTimeZone("America/Pangnirtung")), AMERICA_PARAMARIBO(TimeZone
			.getTimeZone("America/Paramaribo")), AMERICA_PHOENIX(TimeZone.getTimeZone("America/Phoenix")), AMERICA_PORT_AU_PRINCE(TimeZone.getTimeZone("America/Port-au-Prince")), AMERICA_PORT_OF_SPAIN(TimeZone
			.getTimeZone("America/Port_of_Spain")), AMERICA_PORTO_ACRE(TimeZone.getTimeZone("America/Porto_Acre")), AMERICA_PORTO_VELHO(TimeZone.getTimeZone("America/Porto_Velho")), AMERICA_PUERTO_RICO(TimeZone
			.getTimeZone("America/Puerto_Rico")), AMERICA_RAINY_RIVER(TimeZone.getTimeZone("America/Rainy_River")), AMERICA_RANKIN_INLET(TimeZone.getTimeZone("America/Rankin_Inlet")), AMERICA_RECIFE(TimeZone.getTimeZone("America/Recife")), AMERICA_REGINA(
			TimeZone.getTimeZone("America/Regina")), AMERICA_RIO_BRANCO(TimeZone.getTimeZone("America/Rio_Branco")), AMERICA_ROSARIO(TimeZone.getTimeZone("America/Rosario")), AMERICA_SANTIAGO(TimeZone.getTimeZone("America/Santiago")), AMERICA_SANTO_DOMINGO(
			TimeZone.getTimeZone("America/Santo_Domingo")), AMERICA_SAO_PAULO(TimeZone.getTimeZone("America/Sao_Paulo")), AMERICA_SCORESBYSUND(TimeZone.getTimeZone("America/Scoresbysund")), AMERICA_SHIPROCK(TimeZone
			.getTimeZone("America/Shiprock")), AMERICA_ST_JOHNS(TimeZone.getTimeZone("America/St_Johns")), AMERICA_ST_KITTS(TimeZone.getTimeZone("America/St_Kitts")), AMERICA_ST_LUCIA(TimeZone.getTimeZone("America/St_Lucia")), AMERICA_ST_THOMAS(
			TimeZone.getTimeZone("America/St_Thomas")), AMERICA_ST_VINCENT(TimeZone.getTimeZone("America/St_Vincent")), AMERICA_SWIFT_CURRENT(TimeZone.getTimeZone("America/Swift_Current")), AMERICA_TEGUCIGALPA(TimeZone
			.getTimeZone("America/Tegucigalpa")), AMERICA_THULE(TimeZone.getTimeZone("America/Thule")), AMERICA_THUNDER_BAY(TimeZone.getTimeZone("America/Thunder_Bay")), AMERICA_TIJUANA(TimeZone.getTimeZone("America/Tijuana")), AMERICA_TORTOLA(
			TimeZone.getTimeZone("America/Tortola")), AMERICA_VANCOUVER(TimeZone.getTimeZone("America/Vancouver")), AMERICA_VIRGIN(TimeZone.getTimeZone("America/Virgin")), AMERICA_WHITEHORSE(TimeZone.getTimeZone("America/Whitehorse")), AMERICA_WINNIPEG(
			TimeZone.getTimeZone("America/Winnipeg")), AMERICA_YAKUTAT(TimeZone.getTimeZone("America/Yakutat")), AMERICA_YELLOWKNIFE(TimeZone.getTimeZone("America/Yellowknife")), ANTARCTICA_CASEY(TimeZone.getTimeZone("Antarctica/Casey")), ANTARCTICA_DAVIS(
			TimeZone.getTimeZone("Antarctica/Davis")), ANTARCTICA_DUMONTDURVILLE(TimeZone.getTimeZone("Antarctica/DumontDUrville")), ANTARCTICA_MAWSON(TimeZone.getTimeZone("Antarctica/Mawson")), ANTARCTICA_MCMURDO(TimeZone
			.getTimeZone("Antarctica/McMurdo")), ANTARCTICA_PALMER(TimeZone.getTimeZone("Antarctica/Palmer")), ANTARCTICA_ROTHERA(TimeZone.getTimeZone("Antarctica/Rothera")), ANTARCTICA_SOUTH_POLE(TimeZone
			.getTimeZone("Antarctica/South_Pole")), ANTARCTICA_SYOWA(TimeZone.getTimeZone("Antarctica/Syowa")), ANTARCTICA_VOSTOK(TimeZone.getTimeZone("Antarctica/Vostok")), ARCTIC_LONGYEARBYEN(TimeZone.getTimeZone("Arctic/Longyearbyen")), ART(
			TimeZone.getTimeZone("ART")), ASIA_ADEN(TimeZone.getTimeZone("Asia/Aden")), ASIA_ALMATY(TimeZone.getTimeZone("Asia/Almaty")), ASIA_AMMAN(TimeZone.getTimeZone("Asia/Amman")), ASIA_ANADYR(TimeZone.getTimeZone("Asia/Anadyr")), ASIA_AQTAU(
			TimeZone.getTimeZone("Asia/Aqtau")), ASIA_AQTOBE(TimeZone.getTimeZone("Asia/Aqtobe")), ASIA_ASHGABAT(TimeZone.getTimeZone("Asia/Ashgabat")), ASIA_ASHKHABAD(TimeZone.getTimeZone("Asia/Ashkhabad")), ASIA_BAGHDAD(TimeZone
			.getTimeZone("Asia/Baghdad")), ASIA_BAHRAIN(TimeZone.getTimeZone("Asia/Bahrain")), ASIA_BAKU(TimeZone.getTimeZone("Asia/Baku")), ASIA_BANGKOK(TimeZone.getTimeZone("Asia/Bangkok")), ASIA_BEIRUT(TimeZone
			.getTimeZone("Asia/Beirut")), ASIA_BISHKEK(TimeZone.getTimeZone("Asia/Bishkek")), ASIA_BRUNEI(TimeZone.getTimeZone("Asia/Brunei")), ASIA_CALCUTTA(TimeZone.getTimeZone("Asia/Calcutta")), ASIA_CHOIBALSAN(TimeZone
			.getTimeZone("Asia/Choibalsan")), ASIA_CHONGQING(TimeZone.getTimeZone("Asia/Chongqing")), ASIA_CHUNGKING(TimeZone.getTimeZone("Asia/Chungking")), ASIA_COLOMBO(TimeZone.getTimeZone("Asia/Colombo")), ASIA_DACCA(TimeZone
			.getTimeZone("Asia/Dacca")), ASIA_DAMASCUS(TimeZone.getTimeZone("Asia/Damascus")), ASIA_DHAKA(TimeZone.getTimeZone("Asia/Dhaka")), ASIA_DILI(TimeZone.getTimeZone("Asia/Dili")), ASIA_DUBAI(TimeZone.getTimeZone("Asia/Dubai")), ASIA_DUSHANBE(
			TimeZone.getTimeZone("Asia/Dushanbe")), ASIA_GAZA(TimeZone.getTimeZone("Asia/Gaza")), ASIA_HARBIN(TimeZone.getTimeZone("Asia/Harbin")), ASIA_HONG_KONG(TimeZone.getTimeZone("Asia/Hong_Kong")), ASIA_HOVD(TimeZone
			.getTimeZone("Asia/Hovd")), ASIA_IRKUTSK(TimeZone.getTimeZone("Asia/Irkutsk")), ASIA_ISTANBUL(TimeZone.getTimeZone("Asia/Istanbul")), ASIA_JAKARTA(TimeZone.getTimeZone("Asia/Jakarta")), ASIA_JAYAPURA(TimeZone
			.getTimeZone("Asia/Jayapura")), ASIA_JERUSALEM(TimeZone.getTimeZone("Asia/Jerusalem")), ASIA_KABUL(TimeZone.getTimeZone("Asia/Kabul")), ASIA_KAMCHATKA(TimeZone.getTimeZone("Asia/Kamchatka")), ASIA_KARACHI(TimeZone
			.getTimeZone("Asia/Karachi")), ASIA_KASHGAR(TimeZone.getTimeZone("Asia/Kashgar")), ASIA_KATMANDU(TimeZone.getTimeZone("Asia/Katmandu")), ASIA_KRASNOYARSK(TimeZone.getTimeZone("Asia/Krasnoyarsk")), ASIA_KUALA_LUMPUR(TimeZone
			.getTimeZone("Asia/Kuala_Lumpur")), ASIA_KUCHING(TimeZone.getTimeZone("Asia/Kuching")), ASIA_KUWAIT(TimeZone.getTimeZone("Asia/Kuwait")), ASIA_MACAO(TimeZone.getTimeZone("Asia/Macao")), ASIA_MACAU(TimeZone
			.getTimeZone("Asia/Macau")), ASIA_MAGADAN(TimeZone.getTimeZone("Asia/Magadan")), ASIA_MAKASSAR(TimeZone.getTimeZone("Asia/Makassar")), ASIA_MANILA(TimeZone.getTimeZone("Asia/Manila")), ASIA_MUSCAT(TimeZone
			.getTimeZone("Asia/Muscat")), ASIA_NICOSIA(TimeZone.getTimeZone("Asia/Nicosia")), ASIA_NOVOSIBIRSK(TimeZone.getTimeZone("Asia/Novosibirsk")), ASIA_OMSK(TimeZone.getTimeZone("Asia/Omsk")), ASIA_ORAL(TimeZone
			.getTimeZone("Asia/Oral")), ASIA_PHNOM_PENH(TimeZone.getTimeZone("Asia/Phnom_Penh")), ASIA_PONTIANAK(TimeZone.getTimeZone("Asia/Pontianak")), ASIA_PYONGYANG(TimeZone.getTimeZone("Asia/Pyongyang")), ASIA_QATAR(TimeZone
			.getTimeZone("Asia/Qatar")), ASIA_QYZYLORDA(TimeZone.getTimeZone("Asia/Qyzylorda")), ASIA_RANGOON(TimeZone.getTimeZone("Asia/Rangoon")), ASIA_RIYADH(TimeZone.getTimeZone("Asia/Riyadh")), ASIA_RIYADH87(TimeZone
			.getTimeZone("Asia/Riyadh87")), ASIA_RIYADH88(TimeZone.getTimeZone("Asia/Riyadh88")), ASIA_RIYADH89(TimeZone.getTimeZone("Asia/Riyadh89")), ASIA_SAIGON(TimeZone.getTimeZone("Asia/Saigon")), ASIA_SAKHALIN(TimeZone
			.getTimeZone("Asia/Sakhalin")), ASIA_SAMARKAND(TimeZone.getTimeZone("Asia/Samarkand")), ASIA_SEOUL(TimeZone.getTimeZone("Asia/Seoul")), ASIA_SHANGHAI(TimeZone.getTimeZone("Asia/Shanghai")), ASIA_SINGAPORE(TimeZone
			.getTimeZone("Asia/Singapore")), ASIA_TAIPEI(TimeZone.getTimeZone("Asia/Taipei")), ASIA_TASHKENT(TimeZone.getTimeZone("Asia/Tashkent")), ASIA_TBILISI(TimeZone.getTimeZone("Asia/Tbilisi")), ASIA_TEHRAN(TimeZone
			.getTimeZone("Asia/Tehran")), ASIA_TEL_AVIV(TimeZone.getTimeZone("Asia/Tel_Aviv")), ASIA_THIMBU(TimeZone.getTimeZone("Asia/Thimbu")), ASIA_THIMPHU(TimeZone.getTimeZone("Asia/Thimphu")), ASIA_TOKYO(TimeZone
			.getTimeZone("Asia/Tokyo")), ASIA_UJUNG_PANDANG(TimeZone.getTimeZone("Asia/Ujung_Pandang")), ASIA_ULAANBAATAR(TimeZone.getTimeZone("Asia/Ulaanbaatar")), ASIA_ULAN_BATOR(TimeZone.getTimeZone("Asia/Ulan_Bator")), ASIA_URUMQI(
			TimeZone.getTimeZone("Asia/Urumqi")), ASIA_VIENTIANE(TimeZone.getTimeZone("Asia/Vientiane")), ASIA_VLADIVOSTOK(TimeZone.getTimeZone("Asia/Vladivostok")), ASIA_YAKUTSK(TimeZone.getTimeZone("Asia/Yakutsk")), ASIA_YEKATERINBURG(
			TimeZone.getTimeZone("Asia/Yekaterinburg")), ASIA_YEREVAN(TimeZone.getTimeZone("Asia/Yerevan")), AST(TimeZone.getTimeZone("AST")), ATLANTIC_AZORES(TimeZone.getTimeZone("Atlantic/Azores")), ATLANTIC_BERMUDA(TimeZone
			.getTimeZone("Atlantic/Bermuda")), ATLANTIC_CANARY(TimeZone.getTimeZone("Atlantic/Canary")), ATLANTIC_CAPE_VERDE(TimeZone.getTimeZone("Atlantic/Cape_Verde")), ATLANTIC_FAEROE(TimeZone.getTimeZone("Atlantic/Faeroe")), ATLANTIC_JAN_MAYEN(
			TimeZone.getTimeZone("Atlantic/Jan_Mayen")), ATLANTIC_MADEIRA(TimeZone.getTimeZone("Atlantic/Madeira")), ATLANTIC_REYKJAVIK(TimeZone.getTimeZone("Atlantic/Reykjavik")), ATLANTIC_SOUTH_GEORGIA(TimeZone
			.getTimeZone("Atlantic/South_Georgia")), ATLANTIC_ST_HELENA(TimeZone.getTimeZone("Atlantic/St_Helena")), ATLANTIC_STANLEY(TimeZone.getTimeZone("Atlantic/Stanley")), AUSTRALIA_ACT(TimeZone.getTimeZone("Australia/ACT")), AUSTRALIA_ADELAIDE(
			TimeZone.getTimeZone("Australia/Adelaide")), AUSTRALIA_BRISBANE(TimeZone.getTimeZone("Australia/Brisbane")), AUSTRALIA_BROKEN_HILL(TimeZone.getTimeZone("Australia/Broken_Hill")), AUSTRALIA_CANBERRA(TimeZone
			.getTimeZone("Australia/Canberra")), AUSTRALIA_DARWIN(TimeZone.getTimeZone("Australia/Darwin")), AUSTRALIA_HOBART(TimeZone.getTimeZone("Australia/Hobart")), AUSTRALIA_LHI(TimeZone.getTimeZone("Australia/LHI")), AUSTRALIA_LINDEMAN(
			TimeZone.getTimeZone("Australia/Lindeman")), AUSTRALIA_LORD_HOWE(TimeZone.getTimeZone("Australia/Lord_Howe")), AUSTRALIA_MELBOURNE(TimeZone.getTimeZone("Australia/Melbourne")), AUSTRALIA_NORTH(TimeZone
			.getTimeZone("Australia/North")), AUSTRALIA_NSW(TimeZone.getTimeZone("Australia/NSW")), AUSTRALIA_PERTH(TimeZone.getTimeZone("Australia/Perth")), AUSTRALIA_QUEENSLAND(TimeZone.getTimeZone("Australia/Queensland")), AUSTRALIA_SOUTH(
			TimeZone.getTimeZone("Australia/South")), AUSTRALIA_SYDNEY(TimeZone.getTimeZone("Australia/Sydney")), AUSTRALIA_TASMANIA(TimeZone.getTimeZone("Australia/Tasmania")), AUSTRALIA_VICTORIA(TimeZone.getTimeZone("Australia/Victoria")), AUSTRALIA_WEST(
			TimeZone.getTimeZone("Australia/West")), AUSTRALIA_YANCOWINNA(TimeZone.getTimeZone("Australia/Yancowinna")), BET(TimeZone.getTimeZone("BET")), BRAZIL_ACRE(TimeZone.getTimeZone("Brazil/Acre")), BRAZIL_DENORONHA(TimeZone
			.getTimeZone("Brazil/DeNoronha")), BRAZIL_EAST(TimeZone.getTimeZone("Brazil/East")), BRAZIL_WEST(TimeZone.getTimeZone("Brazil/West")), BST(TimeZone.getTimeZone("BST")), CANADA_ATLANTIC(TimeZone.getTimeZone("Canada/Atlantic")), CANADA_CENTRAL(
			TimeZone.getTimeZone("Canada/Central")), CANADA_EAST_SASKATCHEWAN(TimeZone.getTimeZone("Canada/East-Saskatchewan")), CANADA_EASTERN(TimeZone.getTimeZone("Canada/Eastern")), CANADA_MOUNTAIN(TimeZone
			.getTimeZone("Canada/Mountain")), CANADA_NEWFOUNDLAND(TimeZone.getTimeZone("Canada/Newfoundland")), CANADA_PACIFIC(TimeZone.getTimeZone("Canada/Pacific")), CANADA_SASKATCHEWAN(TimeZone.getTimeZone("Canada/Saskatchewan")), CANADA_YUKON(
			TimeZone.getTimeZone("Canada/Yukon")), CAT(TimeZone.getTimeZone("CAT")), CET(TimeZone.getTimeZone("CET")), CHILE_CONTINENTAL(TimeZone.getTimeZone("Chile/Continental")), CHILE_EASTERISLAND(TimeZone
			.getTimeZone("Chile/EasterIsland")), CNT(TimeZone.getTimeZone("CNT")), CST(TimeZone.getTimeZone("CST")), CST6CDT(TimeZone.getTimeZone("CST6CDT")), CTT(TimeZone.getTimeZone("CTT")), CUBA(TimeZone.getTimeZone("Cuba")), EAT(
			TimeZone.getTimeZone("EAT")), ECT(TimeZone.getTimeZone("ECT")), EET(TimeZone.getTimeZone("EET")), EGYPT(TimeZone.getTimeZone("Egypt")), EIRE(TimeZone.getTimeZone("Eire")), EST(TimeZone.getTimeZone("EST")), EST5EDT(TimeZone
			.getTimeZone("EST5EDT")), ETC_GMT(TimeZone.getTimeZone("Etc/GMT")), ETC_GMT_PLUS_0(TimeZone.getTimeZone("Etc/GMT+0")), ETC_GMT_PLUS_1(TimeZone.getTimeZone("Etc/GMT+1")), ETC_GMT_PLUS_10(TimeZone.getTimeZone("Etc/GMT+10")), ETC_GMT_PLUS_11(
			TimeZone.getTimeZone("Etc/GMT+11")), ETC_GMT_PLUS_12(TimeZone.getTimeZone("Etc/GMT+12")), ETC_GMT_PLUS_2(TimeZone.getTimeZone("Etc/GMT+2")), ETC_GMT_PLUS_3(TimeZone.getTimeZone("Etc/GMT+3")), ETC_GMT_PLUS_4(TimeZone
			.getTimeZone("Etc/GMT+4")), ETC_GMT_PLUS_5(TimeZone.getTimeZone("Etc/GMT+5")), ETC_GMT_PLUS_6(TimeZone.getTimeZone("Etc/GMT+6")), ETC_GMT_PLUS_7(TimeZone.getTimeZone("Etc/GMT+7")), ETC_GMT_PLUS_8(TimeZone
			.getTimeZone("Etc/GMT+8")), ETC_GMT_PLUS_9(TimeZone.getTimeZone("Etc/GMT+9")), ETC_GMT_MINUS_0(TimeZone.getTimeZone("Etc/GMT-0")), ETC_GMT_MINUS_1(TimeZone.getTimeZone("Etc/GMT-1")), ETC_GMT_MINUS_10(TimeZone
			.getTimeZone("Etc/GMT-10")), ETC_GMT_MINUS_11(TimeZone.getTimeZone("Etc/GMT-11")), ETC_GMT_MINUS_12(TimeZone.getTimeZone("Etc/GMT-12")), ETC_GMT_MINUS_13(TimeZone.getTimeZone("Etc/GMT-13")), ETC_GMT_MINUS_14(TimeZone
			.getTimeZone("Etc/GMT-14")), ETC_GMT_MINUS_2(TimeZone.getTimeZone("Etc/GMT-2")), ETC_GMT_MINUS_3(TimeZone.getTimeZone("Etc/GMT-3")), ETC_GMT_MINUS_4(TimeZone.getTimeZone("Etc/GMT-4")), ETC_GMT_MINUS_5(TimeZone
			.getTimeZone("Etc/GMT-5")), ETC_GMT_MINUS_6(TimeZone.getTimeZone("Etc/GMT-6")), ETC_GMT_MINUS_7(TimeZone.getTimeZone("Etc/GMT-7")), ETC_GMT_MINUS_8(TimeZone.getTimeZone("Etc/GMT-8")), ETC_GMT_MINUS_9(TimeZone
			.getTimeZone("Etc/GMT-9")), ETC_GMT0(TimeZone.getTimeZone("Etc/GMT0")), ETC_GREENWICH(TimeZone.getTimeZone("Etc/Greenwich")), ETC_UCT(TimeZone.getTimeZone("Etc/UCT")), ETC_UNIVERSAL(TimeZone.getTimeZone("Etc/Universal")), ETC_UTC(
			TimeZone.getTimeZone("Etc/UTC")), ETC_ZULU(TimeZone.getTimeZone("Etc/Zulu")), EUROPE_AMSTERDAM(TimeZone.getTimeZone("Europe/Amsterdam")), EUROPE_ANDORRA(TimeZone.getTimeZone("Europe/Andorra")), EUROPE_ATHENS(TimeZone
			.getTimeZone("Europe/Athens")), EUROPE_BELFAST(TimeZone.getTimeZone("Europe/Belfast")), EUROPE_BELGRADE(TimeZone.getTimeZone("Europe/Belgrade")), EUROPE_BERLIN(TimeZone.getTimeZone("Europe/Berlin")), EUROPE_BRATISLAVA(TimeZone
			.getTimeZone("Europe/Bratislava")), EUROPE_BRUSSELS(TimeZone.getTimeZone("Europe/Brussels")), EUROPE_BUCHAREST(TimeZone.getTimeZone("Europe/Bucharest")), EUROPE_BUDAPEST(TimeZone.getTimeZone("Europe/Budapest")), EUROPE_CHISINAU(
			TimeZone.getTimeZone("Europe/Chisinau")), EUROPE_COPENHAGEN(TimeZone.getTimeZone("Europe/Copenhagen")), EUROPE_DUBLIN(TimeZone.getTimeZone("Europe/Dublin")), EUROPE_GIBRALTAR(TimeZone.getTimeZone("Europe/Gibraltar")), EUROPE_HELSINKI(
			TimeZone.getTimeZone("Europe/Helsinki")), EUROPE_ISTANBUL(TimeZone.getTimeZone("Europe/Istanbul")), EUROPE_KALININGRAD(TimeZone.getTimeZone("Europe/Kaliningrad")), EUROPE_KIEV(TimeZone.getTimeZone("Europe/Kiev")), EUROPE_LISBON(
			TimeZone.getTimeZone("Europe/Lisbon")), EUROPE_LJUBLJANA(TimeZone.getTimeZone("Europe/Ljubljana")), EUROPE_LONDON(TimeZone.getTimeZone("Europe/London")), EUROPE_LUXEMBOURG(TimeZone.getTimeZone("Europe/Luxembourg")), EUROPE_MADRID(
			TimeZone.getTimeZone("Europe/Madrid")), EUROPE_MALTA(TimeZone.getTimeZone("Europe/Malta")), EUROPE_MINSK(TimeZone.getTimeZone("Europe/Minsk")), EUROPE_MONACO(TimeZone.getTimeZone("Europe/Monaco")), EUROPE_MOSCOW(TimeZone
			.getTimeZone("Europe/Moscow")), EUROPE_NICOSIA(TimeZone.getTimeZone("Europe/Nicosia")), EUROPE_OSLO(TimeZone.getTimeZone("Europe/Oslo")), EUROPE_PARIS(TimeZone.getTimeZone("Europe/Paris")), EUROPE_PRAGUE(TimeZone
			.getTimeZone("Europe/Prague")), EUROPE_RIGA(TimeZone.getTimeZone("Europe/Riga")), EUROPE_ROME(TimeZone.getTimeZone("Europe/Rome")), EUROPE_SAMARA(TimeZone.getTimeZone("Europe/Samara")), EUROPE_SAN_MARINO(TimeZone
			.getTimeZone("Europe/San_Marino")), EUROPE_SARAJEVO(TimeZone.getTimeZone("Europe/Sarajevo")), EUROPE_SIMFEROPOL(TimeZone.getTimeZone("Europe/Simferopol")), EUROPE_SKOPJE(TimeZone.getTimeZone("Europe/Skopje")), EUROPE_SOFIA(
			TimeZone.getTimeZone("Europe/Sofia")), EUROPE_STOCKHOLM(TimeZone.getTimeZone("Europe/Stockholm")), EUROPE_TALLINN(TimeZone.getTimeZone("Europe/Tallinn")), EUROPE_TIRANE(TimeZone.getTimeZone("Europe/Tirane")), EUROPE_TIRASPOL(
			TimeZone.getTimeZone("Europe/Tiraspol")), EUROPE_UZHGOROD(TimeZone.getTimeZone("Europe/Uzhgorod")), EUROPE_VADUZ(TimeZone.getTimeZone("Europe/Vaduz")), EUROPE_VATICAN(TimeZone.getTimeZone("Europe/Vatican")), EUROPE_VIENNA(
			TimeZone.getTimeZone("Europe/Vienna")), EUROPE_VILNIUS(TimeZone.getTimeZone("Europe/Vilnius")), EUROPE_WARSAW(TimeZone.getTimeZone("Europe/Warsaw")), EUROPE_ZAGREB(TimeZone.getTimeZone("Europe/Zagreb")), EUROPE_ZAPOROZHYE(
			TimeZone.getTimeZone("Europe/Zaporozhye")), EUROPE_ZURICH(TimeZone.getTimeZone("Europe/Zurich")), GB(TimeZone.getTimeZone("GB")), GB_EIRE(TimeZone.getTimeZone("GB-Eire")), GMT(TimeZone.getTimeZone("GMT")), GMT0(TimeZone
			.getTimeZone("GMT0")), GREENWICH(TimeZone.getTimeZone("Greenwich")), HONGKONG(TimeZone.getTimeZone("Hongkong")), HST(TimeZone.getTimeZone("HST")), ICELAND(TimeZone.getTimeZone("Iceland")), IET(TimeZone.getTimeZone("IET")), INDIAN_ANTANANARIVO(
			TimeZone.getTimeZone("Indian/Antananarivo")), INDIAN_CHAGOS(TimeZone.getTimeZone("Indian/Chagos")), INDIAN_CHRISTMAS(TimeZone.getTimeZone("Indian/Christmas")), INDIAN_COCOS(TimeZone.getTimeZone("Indian/Cocos")), INDIAN_COMORO(
			TimeZone.getTimeZone("Indian/Comoro")), INDIAN_KERGUELEN(TimeZone.getTimeZone("Indian/Kerguelen")), INDIAN_MAHE(TimeZone.getTimeZone("Indian/Mahe")), INDIAN_MALDIVES(TimeZone.getTimeZone("Indian/Maldives")), INDIAN_MAURITIUS(
			TimeZone.getTimeZone("Indian/Mauritius")), INDIAN_MAYOTTE(TimeZone.getTimeZone("Indian/Mayotte")), INDIAN_REUNION(TimeZone.getTimeZone("Indian/Reunion")), IRAN(TimeZone.getTimeZone("Iran")), ISRAEL(TimeZone
			.getTimeZone("Israel")), IST(TimeZone.getTimeZone("IST")), JAMAICA(TimeZone.getTimeZone("Jamaica")), JAPAN(TimeZone.getTimeZone("Japan")), JST(TimeZone.getTimeZone("JST")), KWAJALEIN(TimeZone.getTimeZone("Kwajalein")), LIBYA(
			TimeZone.getTimeZone("Libya")), MET(TimeZone.getTimeZone("MET")), MEXICO_BAJANORTE(TimeZone.getTimeZone("Mexico/BajaNorte")), MEXICO_BAJASUR(TimeZone.getTimeZone("Mexico/BajaSur")), MEXICO_GENERAL(TimeZone
			.getTimeZone("Mexico/General")), MIDEAST_RIYADH87(TimeZone.getTimeZone("Mideast/Riyadh87")), MIDEAST_RIYADH88(TimeZone.getTimeZone("Mideast/Riyadh88")), MIDEAST_RIYADH89(TimeZone.getTimeZone("Mideast/Riyadh89")), MIT(TimeZone
			.getTimeZone("MIT")), MST(TimeZone.getTimeZone("MST")), MST7MDT(TimeZone.getTimeZone("MST7MDT")), NAVAJO(TimeZone.getTimeZone("Navajo")), NET(TimeZone.getTimeZone("NET")), NST(TimeZone.getTimeZone("NST")), NZ(TimeZone
			.getTimeZone("NZ")), NZ_CHAT(TimeZone.getTimeZone("NZ-CHAT")), PACIFIC_APIA(TimeZone.getTimeZone("Pacific/Apia")), PACIFIC_AUCKLAND(TimeZone.getTimeZone("Pacific/Auckland")), PACIFIC_CHATHAM(TimeZone
			.getTimeZone("Pacific/Chatham")), PACIFIC_EASTER(TimeZone.getTimeZone("Pacific/Easter")), PACIFIC_EFATE(TimeZone.getTimeZone("Pacific/Efate")), PACIFIC_ENDERBURY(TimeZone.getTimeZone("Pacific/Enderbury")), PACIFIC_FAKAOFO(
			TimeZone.getTimeZone("Pacific/Fakaofo")), PACIFIC_FIJI(TimeZone.getTimeZone("Pacific/Fiji")), PACIFIC_FUNAFUTI(TimeZone.getTimeZone("Pacific/Funafuti")), PACIFIC_GALAPAGOS(TimeZone.getTimeZone("Pacific/Galapagos")), PACIFIC_GAMBIER(
			TimeZone.getTimeZone("Pacific/Gambier")), PACIFIC_GUADALCANAL(TimeZone.getTimeZone("Pacific/Guadalcanal")), PACIFIC_GUAM(TimeZone.getTimeZone("Pacific/Guam")), PACIFIC_HONOLULU(TimeZone.getTimeZone("Pacific/Honolulu")), PACIFIC_JOHNSTON(
			TimeZone.getTimeZone("Pacific/Johnston")), PACIFIC_KIRITIMATI(TimeZone.getTimeZone("Pacific/Kiritimati")), PACIFIC_KOSRAE(TimeZone.getTimeZone("Pacific/Kosrae")), PACIFIC_KWAJALEIN(TimeZone.getTimeZone("Pacific/Kwajalein")), PACIFIC_MAJURO(
			TimeZone.getTimeZone("Pacific/Majuro")), PACIFIC_MARQUESAS(TimeZone.getTimeZone("Pacific/Marquesas")), PACIFIC_MIDWAY(TimeZone.getTimeZone("Pacific/Midway")), PACIFIC_NAURU(TimeZone.getTimeZone("Pacific/Nauru")), PACIFIC_NIUE(
			TimeZone.getTimeZone("Pacific/Niue")), PACIFIC_NORFOLK(TimeZone.getTimeZone("Pacific/Norfolk")), PACIFIC_NOUMEA(TimeZone.getTimeZone("Pacific/Noumea")), PACIFIC_PAGO_PAGO(TimeZone.getTimeZone("Pacific/Pago_Pago")), PACIFIC_PALAU(
			TimeZone.getTimeZone("Pacific/Palau")), PACIFIC_PITCAIRN(TimeZone.getTimeZone("Pacific/Pitcairn")), PACIFIC_PONAPE(TimeZone.getTimeZone("Pacific/Ponape")), PACIFIC_PORT_MORESBY(TimeZone.getTimeZone("Pacific/Port_Moresby")), PACIFIC_RAROTONGA(
			TimeZone.getTimeZone("Pacific/Rarotonga")), PACIFIC_SAIPAN(TimeZone.getTimeZone("Pacific/Saipan")), PACIFIC_SAMOA(TimeZone.getTimeZone("Pacific/Samoa")), PACIFIC_TAHITI(TimeZone.getTimeZone("Pacific/Tahiti")), PACIFIC_TARAWA(
			TimeZone.getTimeZone("Pacific/Tarawa")), PACIFIC_TONGATAPU(TimeZone.getTimeZone("Pacific/Tongatapu")), PACIFIC_TRUK(TimeZone.getTimeZone("Pacific/Truk")), PACIFIC_WAKE(TimeZone.getTimeZone("Pacific/Wake")), PACIFIC_WALLIS(
			TimeZone.getTimeZone("Pacific/Wallis")), PACIFIC_YAP(TimeZone.getTimeZone("Pacific/Yap")), PLT(TimeZone.getTimeZone("PLT")), PNT(TimeZone.getTimeZone("PNT")), POLAND(TimeZone.getTimeZone("Poland")), PORTUGAL(TimeZone
			.getTimeZone("Portugal")), PRC(TimeZone.getTimeZone("PRC")), PRT(TimeZone.getTimeZone("PRT")), PST(TimeZone.getTimeZone("PST")), PST8PDT(TimeZone.getTimeZone("PST8PDT")), ROK(TimeZone.getTimeZone("ROK")), SINGAPORE(TimeZone
			.getTimeZone("Singapore")), SST(TimeZone.getTimeZone("SST")), SYSTEMV_AST4(TimeZone.getTimeZone("SystemV/AST4")), SYSTEMV_AST4ADT(TimeZone.getTimeZone("SystemV/AST4ADT")), SYSTEMV_CST6(TimeZone.getTimeZone("SystemV/CST6")), SYSTEMV_CST6CDT(
			TimeZone.getTimeZone("SystemV/CST6CDT")), SYSTEMV_EST5(TimeZone.getTimeZone("SystemV/EST5")), SYSTEMV_EST5EDT(TimeZone.getTimeZone("SystemV/EST5EDT")), SYSTEMV_HST10(TimeZone.getTimeZone("SystemV/HST10")), SYSTEMV_MST7(TimeZone
			.getTimeZone("SystemV/MST7")), SYSTEMV_MST7MDT(TimeZone.getTimeZone("SystemV/MST7MDT")), SYSTEMV_PST8(TimeZone.getTimeZone("SystemV/PST8")), SYSTEMV_PST8PDT(TimeZone.getTimeZone("SystemV/PST8PDT")), SYSTEMV_YST9(TimeZone
			.getTimeZone("SystemV/YST9")), SYSTEMV_YST9YDT(TimeZone.getTimeZone("SystemV/YST9YDT")), TURKEY(TimeZone.getTimeZone("Turkey")), UCT(TimeZone.getTimeZone("UCT")), UNIVERSAL(TimeZone.getTimeZone("Universal")), US_ALASKA(TimeZone
			.getTimeZone("US/Alaska")), US_ALEUTIAN(TimeZone.getTimeZone("US/Aleutian")), US_ARIZONA(TimeZone.getTimeZone("US/Arizona")), US_CENTRAL(TimeZone.getTimeZone("US/Central")), US_EAST_INDIANA(TimeZone
			.getTimeZone("US/East-Indiana")), US_EASTERN(TimeZone.getTimeZone("US/Eastern")), US_HAWAII(TimeZone.getTimeZone("US/Hawaii")), US_INDIANA_STARKE(TimeZone.getTimeZone("US/Indiana-Starke")), US_MICHIGAN(TimeZone
			.getTimeZone("US/Michigan")), US_MOUNTAIN(TimeZone.getTimeZone("US/Mountain")), US_PACIFIC(TimeZone.getTimeZone("US/Pacific")), US_PACIFIC_NEW(TimeZone.getTimeZone("US/Pacific-New")), US_SAMOA(TimeZone.getTimeZone("US/Samoa")), UTC(
			TimeZone.getTimeZone("UTC")), VST(TimeZone.getTimeZone("VST")), W_SU(TimeZone.getTimeZone("W-SU")), WET(TimeZone.getTimeZone("WET")), ZULU(TimeZone.getTimeZone("Zulu"));

	private final TimeZone tz;

	private TimeZones(final TimeZone tz) {
		this.tz = tz;
	}

	public final TimeZone getTimeZone() {
		return tz;
	}

}
