package kat.katdapp.contracts

import kat.katdapp.states.SampleState
import net.corda.v5.ledger.contracts.CommandData
import net.corda.v5.ledger.contracts.Contract
import net.corda.v5.ledger.contracts.requireThat
import net.corda.v5.ledger.transactions.LedgerTransaction

class SampleStateContract : Contract {

    override fun verify(tx: LedgerTransaction) {

        //Extract the command from the transaction.
        val commandData = tx.commands[0].value

        //Verify the transaction according to the intention of the transaction
        when (commandData) {
            is Commands.Issue -> requireThat {
                val output = tx.outputsOfType(SampleState::class.java)[0]
                "This transaction should only have one MarsVoucher state as output".using(tx.outputs.size == 1)
                "The output MarsVoucher state should have clear description of the type of Space trip information".using(output.voucherDesc != "")
                null
            }
            /*
            is BoardingTicketContract.Commands.RedeemTicket-> requireThat {
                //Transaction verification will happen in BoardingTicket Contract
            }
            */

        }
    }

    // Used to indicate the transaction's intent.
    interface Commands : CommandData {
        class Issue : Commands
    }

    companion object {
        // This is used to identify our contract when building a transaction.
        const val ID = "com.tutorial.contracts.MarsVoucherContract"
    }
}