package sa.cwad

import sa.model.accounts.AccountsRepository
import sa.model.accounts.InMemoryAccountsRepository
import sa.model.boxes.BoxesRepository
import sa.model.boxes.InMemoryBoxesRepository

object Repositories {

    val accountsRepository: AccountsRepository = InMemoryAccountsRepository()

    val boxesRepository: BoxesRepository = InMemoryBoxesRepository(accountsRepository)

}