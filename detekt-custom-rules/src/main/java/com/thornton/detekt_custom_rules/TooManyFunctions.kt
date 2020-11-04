package com.thornton.detekt_custom_rules

import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtNamedFunction

const val THRESHOLD = 2

class TooManyFunctions : Rule(){

    override val issue = Issue(
        javaClass.simpleName,
        Severity.CodeSmell,
        "This rule reports a file with an excessive function count.",
        Debt.TWENTY_MINS
    )

    private var amount: Int = 0


    override fun visitKtFile(file: KtFile) {
        super.visitKtFile(file)
        if (amount > THRESHOLD) {
            report(
                CodeSmell(issue, Entity.atPackageOrFirstDecl(file),
                message = "The file ${file.name} has $amount function declarations. " +
                        "Threshold is specified with $THRESHOLD.")
            )
        }
        amount = 0
    }

    override fun visitNamedFunction(function: KtNamedFunction) {
        super.visitNamedFunction(function)
        amount++
    }
}