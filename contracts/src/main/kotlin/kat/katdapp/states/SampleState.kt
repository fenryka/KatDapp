package kat.katdapp.states

import com.google.gson.Gson
import kat.katdapp.contracts.SampleStateContract
import net.corda.v5.application.identity.AbstractParty
import net.corda.v5.application.identity.Party
import net.corda.v5.application.utilities.JsonRepresentable
import net.corda.v5.ledger.UniqueIdentifier
import net.corda.v5.ledger.contracts.BelongsToContract
import net.corda.v5.ledger.contracts.LinearState
import net.corda.v5.serialization.annotations.ConstructorForDeserialization

@BelongsToContract(SampleStateContract::class)
data class SampleState @ConstructorForDeserialization constructor (
    val voucherDesc : String,//For example: "One voucher can be exchanged for one ticket to Mars"
    val issuer: Party, //The party who issued the voucher
    val holder: Party, //The party who currently owns the voucher
    override val linearId: UniqueIdentifier,//LinearState required variable
) : LinearState, JsonRepresentable{

    override val participants: List<AbstractParty> get() = listOf<AbstractParty>(issuer,holder)

    private data class MarsVoucherDto(
        val voucherDesc : String,//For example: "One voucher can be exchanged for one ticket to Mars"
        val issuer: String, //The party who issued the voucher
        val holder: String, //The party who currently owns the voucher
        val linearId: String,//LinearState required variable
    )

    private fun toDto(): MarsVoucherDto {
        return MarsVoucherDto(
            voucherDesc,
            issuer.name.toString(),
            holder.name.toString(),
            linearId.toString()
        )
    }

    override fun toJsonString(): String {
        return Gson().toJson(this.toDto())
    }
}

