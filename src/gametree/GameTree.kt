package gametree

import GameState

/**
 * Created by r.makowiecki on 28/02/2018.
 */
typealias Player1Wins = Float
typealias Player2Wins = Float

class GameTree(initialRootNode: Node) {
    var rootNode: Node = initialRootNode
}

open class Node(
        val gameState: GameState,
        var childNodes: List<Node>,
        val parentNode: Node? = null,
        var gamesPlayed: Int = 0,
        var gamesWon: Pair<Player1Wins, Player2Wins> = Pair(0f, 0f)
) {

    open fun updateGamesWon(player1Won: Boolean) {
        gamesWon = if (player1Won) {
            Pair(gamesWon.first + 1, gamesWon.second)
        } else {
            Pair(gamesWon.first, gamesWon.second + 1)
        }
        gamesPlayed += 1

        parentNode?.updateGamesWon(player1Won)
    }

    override fun toString() = "Played/Won = $gamesPlayed/$gamesWon, gamestate = ${gameState.player1} childNodes = \n\t\t\t${childNodes}"
}

class CardDrawingNode(
        val nodeOccurenceProbability: Float,
        gameState: GameState,
        childNodes: List<Node>,
        parentNode: Node? = null,
        gamesPlayed: Int = 0,
        gamesWon: Pair<Player1Wins, Player2Wins> = Pair(0f, 0f)
) : Node(gameState, childNodes, parentNode, gamesPlayed, gamesWon) {

    override fun updateGamesWon(player1Won: Boolean) {
        gamesWon = if (player1Won) {
            Pair(gamesWon.first + 1 * nodeOccurenceProbability, gamesWon.second)
        } else {
            Pair(gamesWon.first, gamesWon.second + 1 * nodeOccurenceProbability)
        }
        gamesPlayed += 1

        parentNode?.updateGamesWon(player1Won)
    }
}
