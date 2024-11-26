"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const readline_sync_1 = __importDefault(require("readline-sync"));
// created variabless for rank
var Rank;
(function (Rank) {
    Rank[Rank["Ace"] = 1] = "Ace";
    Rank[Rank["Two"] = 2] = "Two";
    Rank[Rank["Three"] = 3] = "Three";
    Rank[Rank["Four"] = 4] = "Four";
    Rank[Rank["Five"] = 5] = "Five";
    Rank[Rank["Six"] = 6] = "Six";
    Rank[Rank["Seven"] = 7] = "Seven";
    Rank[Rank["Eight"] = 8] = "Eight";
    Rank[Rank["Nine"] = 9] = "Nine";
    Rank[Rank["Ten"] = 10] = "Ten";
    Rank[Rank["Jack"] = 11] = "Jack";
    Rank[Rank["Queen"] = 12] = "Queen";
    Rank[Rank["King"] = 13] = "King";
})(Rank || (Rank = {}));
// created variables for suit
var Suit;
(function (Suit) {
    Suit[Suit["Hearts"] = 1] = "Hearts";
    Suit[Suit["Diamonds"] = 2] = "Diamonds";
    Suit[Suit["Clubs"] = 3] = "Clubs";
    Suit[Suit["Spades"] = 4] = "Spades";
})(Suit || (Suit = {}));
// Creates deck used in game
class Deck {
    constructor() {
        this.cards = [];
        this.createDeck();
    }
    createDeck() {
        for (let rank = Rank.Ace; rank <= Rank.King; rank++) {
            for (let suit = Suit.Hearts; suit <= Suit.Spades; suit++) {
                // adds string to each card
                if (suit = Suit.Hearts) {
                    this.cards.push(new Card("Hearts", rank));
                }
                if (suit = Suit.Diamonds) {
                    this.cards.push(new Card("Diamonds", rank));
                }
                if (suit = Suit.Clubs) {
                    this.cards.push(new Card("Clubs", rank));
                }
                if (suit = Suit.Spades) {
                    this.cards.push(new Card("Spades", rank));
                }
            }
        }
        this.shuffleDeck(); // always shuffles before game and when deck is created
    }
    shuffleDeck() {
        let pointer = this.cards.length;
        let place;
        // Going down the array.
        while (pointer != 0) {
            // pick a random spot in the array
            place = Math.floor(Math.random() * pointer);
            pointer--;
            // swap with pointer
            [this.cards[pointer], this.cards[place]] = [this.cards[place], this.cards[pointer]];
        }
    }
    // if less than 30 , return true - then will create new deck to "put discarded cards back in" and shuffle
    numCardCheck() {
        return this.cards.length < 30;
    }
    pullCard() {
        return this.cards.pop();
    }
}
class Casino {
    constructor() {
        this.bankroll = 1000; // default bankroll
        this.balance = 100; // default player balance
        this.bet = 5; // default bet
    }
    // called within BlackJack
    // only applied to player!
    setBet(input) {
        if (input > this.balance) {
            this.bet = this.balance;
        }
        else if (input > this.bankroll) { // checks if player bet more than casino balance
            this.bet = this.bankroll;
        }
        else {
            this.bet = input;
        }
    }
    addMoney() {
        this.balance += this.bet; // adds balance
        this.bankroll -= this.bet; // removes balance
    }
    BlackJack() {
        this.balance += this.bet * 1.5;
        this.bankroll -= this.bet * 1.5;
    }
    subtractMoney() {
        this.balance -= this.bet;
        this.bankroll += this.bet;
    }
    currentBet() {
        return this.bet;
    }
    playerBalance() {
        return this.balance;
    }
    casinoBalance() {
        return this.bankroll;
    }
}
class Hand {
    constructor() {
        this.cards = [];
    }
    // called everytime a new game starts
    clearHand() {
        const handLength = this.cards.length;
        for (let i = 0; i < handLength; i++) {
            this.cards.pop(); // removes each card in the player's and dealer's hand
        }
    }
    pullCard(card) {
        this.cards.push(card);
    }
    // retrieves the total score of the hand
    score() {
        let score = 0;
        let aceFlag = false; // default
        for (const card of this.cards) {
            if (card.rank === Rank.Ace) { // if ace is found
                aceFlag = true;
            }
            score += card.value();
        }
        while (score <= 11 && aceFlag) { // ace = 11 if it doesnt cause a bust
            score += 10;
            aceFlag = false;
        }
        return score;
    }
    // returns most recent pulled card
    recentCard() {
        let newestSuit = this.cards[this.cards.length - 1].suit; // recent suit in hand
        let newestRank = this.cards[this.cards.length - 1].rank; // recent rank in hand
        let card;
        // adds the string to the card
        if (newestRank === 1) { // check if ace
            card = "Ace of " + newestSuit;
        }
        else if (newestRank === 11) {
            card = "Jack of " + newestSuit;
        }
        else if (newestRank === 12) {
            card = "Queen of " + newestSuit;
        }
        else if (newestRank === 13) {
            card = "King of " + newestSuit;
        }
        else {
            card = newestRank + " of " + newestSuit;
        }
        return card;
    }
}
//
class Card {
    constructor(suit, rank) {
        this.suit = suit;
        this.rank = rank;
    }
    value() {
        if (this.rank <= 10) { // returns rank of card
            return this.rank;
        }
        else {
            return 10; // must be jack, queen, or king
        }
    }
}
class Blackjack {
    constructor() {
        this.deck = new Deck();
        this.playerHand = new Hand();
        this.dealerHand = new Hand();
        this.casino = new Casino();
    }
    startGame() {
        this.playerHand.clearHand();
        this.dealerHand.clearHand();
        this.casino.setBet(this.casino.currentBet());
        if (this.deck.numCardCheck()) { // if numb of cards less than 30, put discarded cards back and reshuffle.
            console.log("Less than 30 cards left... Adding discarded cards back in deck... reshuffling...");
            this.deck = new Deck();
        }
        if (this.casino.playerBalance() <= 0) {
            console.log("\nYou ran out of money! Ending game...");
            return;
        }
        if (this.casino.casinoBalance() <= 0) {
            console.log("\nCasino ran out of money! Ending game...");
            return;
        }
        console.log("-----------------\nWelcome to Black Jack!");
        console.log("\n...Current bet: ", this.casino.currentBet(), " dollars...");
        console.log("\nCasino balance: ", this.casino.casinoBalance(), " dollars.");
        console.log("Player balance: ", this.casino.playerBalance(), " dollars.");
        this.mainMenu();
    }
    // main menu - will always go back to this until quits game 
    mainMenu() {
        const input = readline_sync_1.default.question("\nDo you want to (p)lay, (c)hange bet, or (q)uit?\n");
        if (input === "p") { // starts game to startGame()
            console.log("\nStarting Game...\n");
            return this.initialDraw();
        }
        else if (input === "q") { // quits
            console.log("\nThanks for Playing!\n");
            return;
        }
        else if (input === "c") { // bet
            let bet = parseInt(readline_sync_1.default.question("\nSet new bet: "), 10);
            while (isNaN(bet) || bet < 0) {
                bet = parseInt(readline_sync_1.default.question("Invalid input! Try again: "), 10);
            }
            this.casino.setBet(bet);
            console.log("Bet set to ", this.casino.currentBet(), "dollars.");
            this.mainMenu();
        }
        else { // other input detected - re-asks question
            console.log("Invalid input. Please try again.");
            this.mainMenu();
        }
    }
    // intial draw to start game
    initialDraw() {
        this.playerHand.pullCard(this.deck.pullCard());
        console.log("You drew: ", this.playerHand.recentCard());
        this.dealerHand.pullCard(this.deck.pullCard());
        console.log("Dealer drew: ", this.dealerHand.recentCard());
        const dealerScore = this.dealerHand.score();
        this.playerHand.pullCard(this.deck.pullCard());
        console.log("You drew: ", this.playerHand.recentCard());
        this.dealerHand.pullCard(this.deck.pullCard());
        console.log("Dealer drew another card...");
        console.log("\nDealer has ", dealerScore, " points shown.");
        console.log("\nYou have", this.playerHand.score(), " points.");
        this.playerTurn();
    }
    playerTurn() {
        const move = readline_sync_1.default.question("\nChoose your move: (h)it, (s)tay. ");
        if (move === "h") {
            this.playerHand.pullCard(this.deck.pullCard());
            console.log("\nYou drew: ", this.playerHand.recentCard());
            console.log("You have ", this.playerHand.score(), " points.");
            if (this.playerHand.score() < 21) { // gives player option to continue
                this.playerTurn();
            }
            else if (this.playerHand.score() === 21) { // Black Jack, forces to dealer
                console.log("\nYou got Black Jack! Dealer's turn...");
                console.log("\nDealer's faced-down card: ", this.dealerHand.recentCard());
                console.log("Dealer has", this.dealerHand.score(), " points.");
                this.dealerTurn(); // can still be a push (tie)
            }
            else {
                console.log("\nYou bust! You lose your bet.\n");
                this.casino.subtractMoney();
                return this.startGame();
            }
        }
        else if (move === "s") {
            console.log("\nDealer's turn...");
            console.log("\nDealer's faced-down card: ", this.dealerHand.recentCard());
            console.log("Dealer has", this.dealerHand.score(), " points.");
            this.dealerTurn(); // goes to dealer!
        }
        else {
            console.log("Invalid input. Please try again.");
            this.playerTurn();
        }
    }
    dealerTurn() {
        if (this.dealerHand.score() < 17) { // hit
            this.dealerHand.pullCard(this.deck.pullCard());
            console.log("\nDealer pulled: ", this.dealerHand.recentCard()); // if less than 17, pulls card
            console.log("Dealer has", this.dealerHand.score(), " points.");
            this.dealerTurn(); // will keeping hitting until it is equal or grater than 17
        }
        else if (16 < this.dealerHand.score() && this.dealerHand.score() < 22) { // stay
            if (this.dealerHand.score() < this.playerHand.score()) { // player has higher points
                if (this.playerHand.score() === 21) { // Player got black jack and didn't tie
                    console.log("\nYou won! You won 1.5x your bet!\n");
                    this.casino.BlackJack();
                    return this.startGame();
                }
                console.log("\nYou have more points. You won your bet!\n");
                this.casino.addMoney();
                return this.startGame();
            }
            else if (this.dealerHand.score() > this.playerHand.score()) { // dealer has more points
                console.log("\nThe dealer has more points. You lost your bet!\n");
                this.casino.subtractMoney();
                return this.startGame();
            }
            else {
                console.log("\nYou tied!\n"); // must have the same score - tie!
                return this.startGame();
            }
        }
        else { // dealer had > 21
            if (this.playerHand.score() === 21) { // Player got black jack and didn't tie
                console.log("\nDealer busted. You won 1.5x your bet!\n");
                this.casino.BlackJack();
                return this.startGame();
            }
            console.log("\nDealer busted. You win your bet!\n");
            this.casino.addMoney();
            return this.startGame();
        }
    }
}
const game = new Blackjack();
game.startGame();
