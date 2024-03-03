package com.cds.itemkeeper.utils

data class Mapping (
    var rootExpression: String,
    var innerExpressions: Map<String, List<String>>
)