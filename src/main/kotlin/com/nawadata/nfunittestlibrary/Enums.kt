package com.nawadata.nfunittestlibrary

class Enums {
  enum class ByOption(val strName: String) {
    ID("@id"),
    Class("@class"),
    Name("@name"),
    Text("text()");
  }

  enum class RandomStringOption {
    Alphabetic,
    Numeric,
    Symbols,
    Alphanumeric,
    AlphanumericWithSymbols
  }

  enum class CaseOption {
    LowerOnly,
    UpperOnly,
    Mixed
  }
}
