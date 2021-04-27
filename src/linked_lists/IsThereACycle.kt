package linked_lists

import linked_lists.given.Node

interface IsThereACycle {
    fun <T> isCyclic(head: Node<T>): Boolean
}