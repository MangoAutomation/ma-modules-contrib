/**
 * Copyright (C) 2025 Radix IoT LLC. All rights reserved.
 */

import template from './currencyConverterDataPointEditor.html';

const CURRENCY_LIST = [
    {
        currencyName: 'Albanian Lek',
        currencySymbol: 'Lek',
        id: 'ALL'
    },
    {
        currencyName: 'East Caribbean Dollar',
        currencySymbol: '$',
        id: 'XCD'
    },
    {
        currencyName: 'Euro',
        currencySymbol: '€',
        id: 'EUR'
    },
    {
        currencyName: 'Barbadian Dollar',
        currencySymbol: '$',
        id: 'BBD'
    },
    {
        currencyName: 'Bhutanese Ngultrum',
        id: 'BTN'
    },
    {
        currencyName: 'Brunei Dollar',
        currencySymbol: '$',
        id: 'BND'
    },
    {
        currencyName: 'Central African CFA Franc',
        id: 'XAF'
    },
    {
        currencyName: 'Cuban Peso',
        currencySymbol: '$',
        id: 'CUP'
    },
    {
        currencyName: 'United States Dollar',
        currencySymbol: '$',
        id: 'USD'
    },
    {
        currencyName: 'Falkland Islands Pound',
        currencySymbol: '£',
        id: 'FKP'
    },
    {
        currencyName: 'Gibraltar Pound',
        currencySymbol: '£',
        id: 'GIP'
    },
    {
        currencyName: 'Hungarian Forint',
        currencySymbol: 'Ft',
        id: 'HUF'
    },
    {
        currencyName: 'Iranian Rial',
        currencySymbol: '﷼',
        id: 'IRR'
    },
    {
        currencyName: 'Jamaican Dollar',
        currencySymbol: 'J$',
        id: 'JMD'
    },
    {
        currencyName: 'Australian Dollar',
        currencySymbol: '$',
        id: 'AUD'
    },
    {
        currencyName: 'Lao Kip',
        currencySymbol: '₭',
        id: 'LAK'
    },
    {
        currencyName: 'Libyan Dinar',
        id: 'LYD'
    },
    {
        currencyName: 'Macedonian Denar',
        currencySymbol: 'ден',
        id: 'MKD'
    },
    {
        currencyName: 'West African CFA Franc',
        id: 'XOF'
    },
    {
        currencyName: 'New Zealand Dollar',
        currencySymbol: '$',
        id: 'NZD'
    },
    {
        currencyName: 'Omani Rial',
        currencySymbol: '﷼',
        id: 'OMR'
    },
    {
        currencyName: 'Papua New Guinean Kina',
        id: 'PGK'
    },
    {
        currencyName: 'Rwandan Franc',
        id: 'RWF'
    },
    {
        currencyName: 'Samoan Tala',
        id: 'WST'
    },
    {
        currencyName: 'Serbian Dinar',
        currencySymbol: 'Дин.',
        id: 'RSD'
    },
    {
        currencyName: 'Swedish Krona',
        currencySymbol: 'kr',
        id: 'SEK'
    },
    {
        currencyName: 'Tanzanian Shilling',
        currencySymbol: 'TSh',
        id: 'TZS'
    },
    {
        currencyName: 'Armenian Dram',
        id: 'AMD'
    },
    {
        currencyName: 'Bahamian Dollar',
        currencySymbol: '$',
        id: 'BSD'
    },
    {
        currencyName: 'Bosnia And Herzegovina Konvertibilna Marka',
        currencySymbol: 'KM',
        id: 'BAM'
    },
    {
        currencyName: 'Cape Verdean Escudo',
        id: 'CVE'
    },
    {
        currencyName: 'Chinese Yuan',
        currencySymbol: '¥',
        id: 'CNY'
    },
    {
        currencyName: 'Costa Rican Colon',
        currencySymbol: '₡',
        id: 'CRC'
    },
    {
        currencyName: 'Czech Koruna',
        currencySymbol: 'Kč',
        id: 'CZK'
    },
    {
        currencyName: 'Eritrean Nakfa',
        id: 'ERN'
    },
    {
        currencyName: 'Georgian Lari',
        id: 'GEL'
    },
    {
        currencyName: 'Haitian Gourde',
        id: 'HTG'
    },
    {
        currencyName: 'Indian Rupee',
        currencySymbol: '₹',
        id: 'INR'
    },
    {
        currencyName: 'Jordanian Dinar',
        id: 'JOD'
    },
    {
        currencyName: 'South Korean Won',
        currencySymbol: '₩',
        id: 'KRW'
    },
    {
        currencyName: 'Lebanese Lira',
        currencySymbol: '£',
        id: 'LBP'
    },
    {
        currencyName: 'Malawian Kwacha',
        id: 'MWK'
    },
    {
        currencyName: 'Mauritanian Ouguiya',
        id: 'MRO'
    },
    {
        currencyName: 'Mozambican Metical',
        id: 'MZN'
    },
    {
        currencyName: 'Netherlands Antillean Gulden',
        currencySymbol: 'ƒ',
        id: 'ANG'
    },
    {
        currencyName: 'Peruvian Nuevo Sol',
        currencySymbol: 'S/.',
        id: 'PEN'
    },
    {
        currencyName: 'Qatari Riyal',
        currencySymbol: '﷼',
        id: 'QAR'
    },
    {
        currencyName: 'Sao Tome And Principe Dobra',
        id: 'STD'
    },
    {
        currencyName: 'Sierra Leonean Leone',
        id: 'SLL'
    },
    {
        currencyName: 'Somali Shilling',
        currencySymbol: 'S',
        id: 'SOS'
    },
    {
        currencyName: 'Sudanese Pound',
        id: 'SDG'
    },
    {
        currencyName: 'Syrian Pound',
        currencySymbol: '£',
        id: 'SYP'
    },
    {
        currencyName: 'Angolan Kwanza',
        id: 'AOA'
    },
    {
        currencyName: 'Aruban Florin',
        currencySymbol: 'ƒ',
        id: 'AWG'
    },
    {
        currencyName: 'Bahraini Dinar',
        id: 'BHD'
    },
    {
        currencyName: 'Belize Dollar',
        currencySymbol: 'BZ$',
        id: 'BZD'
    },
    {
        currencyName: 'Botswana Pula',
        currencySymbol: 'P',
        id: 'BWP'
    },
    {
        currencyName: 'Burundi Franc',
        id: 'BIF'
    },
    {
        currencyName: 'Cayman Islands Dollar',
        currencySymbol: '$',
        id: 'KYD'
    },
    {
        currencyName: 'Colombian Peso',
        currencySymbol: '$',
        id: 'COP'
    },
    {
        currencyName: 'Danish Krone',
        currencySymbol: 'kr',
        id: 'DKK'
    },
    {
        currencyName: 'Guatemalan Quetzal',
        currencySymbol: 'Q',
        id: 'GTQ'
    },
    {
        currencyName: 'Honduran Lempira',
        currencySymbol: 'L',
        id: 'HNL'
    },
    {
        currencyName: 'Indonesian Rupiah',
        currencySymbol: 'Rp',
        id: 'IDR'
    },
    {
        currencyName: 'Israeli New Sheqel',
        currencySymbol: '₪',
        id: 'ILS'
    },
    {
        currencyName: 'Kazakhstani Tenge',
        currencySymbol: 'лв',
        id: 'KZT'
    },
    {
        currencyName: 'Kuwaiti Dinar',
        id: 'KWD'
    },
    {
        currencyName: 'Lesotho Loti',
        id: 'LSL'
    },
    {
        currencyName: 'Malaysian Ringgit',
        currencySymbol: 'RM',
        id: 'MYR'
    },
    {
        currencyName: 'Mauritian Rupee',
        currencySymbol: '₨',
        id: 'MUR'
    },
    {
        currencyName: 'Mongolian Tugrik',
        currencySymbol: '₮',
        id: 'MNT'
    },
    {
        currencyName: 'Myanma Kyat',
        id: 'MMK'
    },
    {
        currencyName: 'Nigerian Naira',
        currencySymbol: '₦',
        id: 'NGN'
    },
    {
        currencyName: 'Panamanian Balboa',
        currencySymbol: 'B/.',
        id: 'PAB'
    },
    {
        currencyName: 'Philippine Peso',
        currencySymbol: '₱',
        id: 'PHP'
    },
    {
        currencyName: 'Romanian Leu',
        currencySymbol: 'lei',
        id: 'RON'
    },
    {
        currencyName: 'Saudi Riyal',
        currencySymbol: '﷼',
        id: 'SAR'
    },
    {
        currencyName: 'Singapore Dollar',
        currencySymbol: '$',
        id: 'SGD'
    },
    {
        currencyName: 'South African Rand',
        currencySymbol: 'R',
        id: 'ZAR'
    },
    {
        currencyName: 'Surinamese Dollar',
        currencySymbol: '$',
        id: 'SRD'
    },
    {
        currencyName: 'New Taiwan Dollar',
        currencySymbol: 'NT$',
        id: 'TWD'
    },
    {
        currencyName: 'Paanga',
        id: 'TOP'
    },
    {
        currencyName: 'Venezuelan Bolivar',
        id: 'VEF'
    },
    {
        currencyName: 'Algerian Dinar',
        id: 'DZD'
    },
    {
        currencyName: 'Argentine Peso',
        currencySymbol: '$',
        id: 'ARS'
    },
    {
        currencyName: 'Azerbaijani Manat',
        currencySymbol: 'ман',
        id: 'AZN'
    },
    {
        currencyName: 'Belarusian Ruble',
        currencySymbol: 'p.',
        id: 'BYR'
    },
    {
        currencyName: 'Bolivian Boliviano',
        currencySymbol: '$b',
        id: 'BOB'
    },
    {
        currencyName: 'Bulgarian Lev',
        currencySymbol: 'лв',
        id: 'BGN'
    },
    {
        currencyName: 'Canadian Dollar',
        currencySymbol: '$',
        id: 'CAD'
    },
    {
        currencyName: 'Chilean Peso',
        currencySymbol: '$',
        id: 'CLP'
    },
    {
        currencyName: 'Congolese Franc',
        id: 'CDF'
    },
    {
        currencyName: 'Dominican Peso',
        currencySymbol: 'RD$',
        id: 'DOP'
    },
    {
        currencyName: 'Fijian Dollar',
        currencySymbol: '$',
        id: 'FJD'
    },
    {
        currencyName: 'Gambian Dalasi',
        id: 'GMD'
    },
    {
        currencyName: 'Guyanese Dollar',
        currencySymbol: '$',
        id: 'GYD'
    },
    {
        currencyName: 'Icelandic Króna',
        currencySymbol: 'kr',
        id: 'ISK'
    },
    {
        currencyName: 'Iraqi Dinar',
        id: 'IQD'
    },
    {
        currencyName: 'Japanese Yen',
        currencySymbol: '¥',
        id: 'JPY'
    },
    {
        currencyName: 'North Korean Won',
        currencySymbol: '₩',
        id: 'KPW'
    },
    {
        currencyName: 'Latvian Lats',
        currencySymbol: 'Ls',
        id: 'LVL'
    },
    {
        currencyName: 'Swiss Franc',
        currencySymbol: 'Fr.',
        id: 'CHF'
    },
    {
        currencyName: 'Malagasy Ariary',
        id: 'MGA'
    },
    {
        currencyName: 'Moldovan Leu',
        id: 'MDL'
    },
    {
        currencyName: 'Moroccan Dirham',
        id: 'MAD'
    },
    {
        currencyName: 'Nepalese Rupee',
        currencySymbol: '₨',
        id: 'NPR'
    },
    {
        currencyName: 'Nicaraguan Cordoba',
        currencySymbol: 'C$',
        id: 'NIO'
    },
    {
        currencyName: 'Pakistani Rupee',
        currencySymbol: '₨',
        id: 'PKR'
    },
    {
        currencyName: 'Paraguayan Guarani',
        currencySymbol: 'Gs',
        id: 'PYG'
    },
    {
        currencyName: 'Saint Helena Pound',
        currencySymbol: '£',
        id: 'SHP'
    },
    {
        currencyName: 'Seychellois Rupee',
        currencySymbol: '₨',
        id: 'SCR'
    },
    {
        currencyName: 'Solomon Islands Dollar',
        currencySymbol: '$',
        id: 'SBD'
    },
    {
        currencyName: 'Sri Lankan Rupee',
        currencySymbol: '₨',
        id: 'LKR'
    },
    {
        currencyName: 'Thai Baht',
        currencySymbol: '฿',
        id: 'THB'
    },
    {
        currencyName: 'Turkish New Lira',
        id: 'TRY'
    },
    {
        currencyName: 'UAE Dirham',
        id: 'AED'
    },
    {
        currencyName: 'Vanuatu Vatu',
        id: 'VUV'
    },
    {
        currencyName: 'Yemeni Rial',
        currencySymbol: '﷼',
        id: 'YER'
    },
    {
        currencyName: 'Afghan Afghani',
        currencySymbol: '؋',
        id: 'AFN'
    },
    {
        currencyName: 'Bangladeshi Taka',
        id: 'BDT'
    },
    {
        currencyName: 'Brazilian Real',
        currencySymbol: 'R$',
        id: 'BRL'
    },
    {
        currencyName: 'Cambodian Riel',
        currencySymbol: '៛',
        id: 'KHR'
    },
    {
        currencyName: 'Comorian Franc',
        id: 'KMF'
    },
    {
        currencyName: 'Croatian Kuna',
        currencySymbol: 'kn',
        id: 'HRK'
    },
    {
        currencyName: 'Djiboutian Franc',
        id: 'DJF'
    },
    {
        currencyName: 'Egyptian Pound',
        currencySymbol: '£',
        id: 'EGP'
    },
    {
        currencyName: 'Ethiopian Birr',
        id: 'ETB'
    },
    {
        currencyName: 'CFP Franc',
        id: 'XPF'
    },
    {
        currencyName: 'Ghanaian Cedi',
        id: 'GHS'
    },
    {
        currencyName: 'Guinean Franc',
        id: 'GNF'
    },
    {
        currencyName: 'Hong Kong Dollar',
        currencySymbol: '$',
        id: 'HKD'
    },
    {
        currencyName: 'Special Drawing Rights',
        id: 'XDR'
    },
    {
        currencyName: 'Kenyan Shilling',
        currencySymbol: 'KSh',
        id: 'KES'
    },
    {
        currencyName: 'Kyrgyzstani Som',
        currencySymbol: 'лв',
        id: 'KGS'
    },
    {
        currencyName: 'Liberian Dollar',
        currencySymbol: '$',
        id: 'LRD'
    },
    {
        currencyName: 'Macanese Pataca',
        id: 'MOP'
    },
    {
        currencyName: 'Maldivian Rufiyaa',
        id: 'MVR'
    },
    {
        currencyName: 'Mexican Peso',
        currencySymbol: '$',
        id: 'MXN'
    },
    {
        currencyName: 'Namibian Dollar',
        currencySymbol: '$',
        id: 'NAD'
    },
    {
        currencyName: 'Norwegian Krone',
        currencySymbol: 'kr',
        id: 'NOK'
    },
    {
        currencyName: 'Polish Zloty',
        currencySymbol: 'zł',
        id: 'PLN'
    },
    {
        currencyName: 'Russian Ruble',
        currencySymbol: 'руб',
        id: 'RUB'
    },
    {
        currencyName: 'Swazi Lilangeni',
        id: 'SZL'
    },
    {
        currencyName: 'Tajikistani Somoni',
        id: 'TJS'
    },
    {
        currencyName: 'Trinidad and Tobago Dollar',
        currencySymbol: 'TT$',
        id: 'TTD'
    },
    {
        currencyName: 'Ugandan Shilling',
        currencySymbol: 'USh',
        id: 'UGX'
    },
    {
        currencyName: 'Uruguayan Peso',
        currencySymbol: '$U',
        id: 'UYU'
    },
    {
        currencyName: 'Vietnamese Dong',
        currencySymbol: '₫',
        id: 'VND'
    },
    {
        currencyName: 'Tunisian Dinar',
        id: 'TND'
    },
    {
        currencyName: 'Ukrainian Hryvnia',
        currencySymbol: '₴',
        id: 'UAH'
    },
    {
        currencyName: 'Uzbekistani Som',
        currencySymbol: 'лв',
        id: 'UZS'
    },
    {
        currencyName: 'Turkmenistan Manat',
        id: 'TMT'
    },
    {
        currencyName: 'British Pound',
        currencySymbol: '£',
        id: 'GBP'
    },
    {
        currencyName: 'Zambian Kwacha',
        id: 'ZMW'
    },
    {
        currencyName: 'Bitcoin',
        currencySymbol: 'BTC',
        id: 'BTC'
    },
    {
        currencyName: 'New Belarusian Ruble',
        currencySymbol: 'p.',
        id: 'BYN'
    },
    {
        currencyName: 'Bermudan Dollar',
        id: 'BMD'
    },
    {
        currencyName: 'Guernsey Pound',
        id: 'GGP'
    },
    {
        currencyName: 'Chilean Unit Of Account',
        id: 'CLF'
    },
    {
        currencyName: 'Cuban Convertible Peso',
        id: 'CUC'
    },
    {
        currencyName: 'Manx pound',
        id: 'IMP'
    },
    {
        currencyName: 'Jersey Pound',
        id: 'JEP'
    },
    {
        currencyName: 'Salvadoran Colón',
        id: 'SVC'
    },
    {
        currencyName: 'Old Zambian Kwacha',
        id: 'ZMK'
    },
    {
        currencyName: 'Silver (troy ounce)',
        id: 'XAG'
    },
    {
        currencyName: 'Zimbabwean Dollar',
        id: 'ZWL'
    }
];

class CurrencyConverterDataPointEditorController {
    static get $$ngIsClass() {
        return true;
    }

    static get $inject() {
        return [];
    }

    $onInit() {
        this.showCurrencyUnit = false;
        this.currencyList = CURRENCY_LIST;
        this.setInitCurrency();
    }

    setInitCurrency() {
        const { fromCurrencyId, toCurrencyId } = this.dataPoint.pointLocator;

        this.fromCurrency = CURRENCY_LIST.find((currency) => currency.id === fromCurrencyId);
        this.toCurrency = CURRENCY_LIST.find((currency) => currency.id === toCurrencyId);

        if (this.toCurrency) {
            this.setSuffix()
        }
    }

    setToDp(setTo) {
        this.dataPoint.pointLocator[`${setTo}Id`] = this[setTo].id;

        this.setSuffix();
    }

    setSuffix(){
        if (this.showCurrencyUnit){
            this.dataPoint.textRenderer.suffix = this.toCurrency.currencySymbol || this.toCurrency.id;
        } else {
            this.dataPoint.textRenderer.suffix = this.toCurrency.id;
        }
    }
}

export default {
    template,
    controller: CurrencyConverterDataPointEditorController,
    bindings: {
        dataPoint: '<point'
    },
    require: {
        pointEditor: '^maDataPointEditor'
    }
};
