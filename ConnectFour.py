import random
from tkinter import *
from tkinter import messagebox
from time import sleep

class Connect4():

    def __init__(self, width, height):
        self.width = width
        self.height = height
        self.data = []
        
        for row in range(self.height):
            boardRow = []
            for col in range(self.width):
                boardRow += [' ']
            self.data += [boardRow]
        
    def __repr__(self):
        #print out rows & cols
        s = ''   # the string to return
        for row in range(self.height):
            s += '|'   # add the separator character
            for col in range( self.width ):
                s += self.data[row][col] + '|'
            s += '\n'
        #print out the horizontal separator
        #(your code goes here)
        for row in range(self.width):
            s += '--'
        s += '-'
        s += '\n'
        # print out indices of each column # using mod if greater than 9,
        # for spacing issues
        #(your code goes here)
        n = 0
        for row in range(self.width):
            s += ' '
            s += str(n)
            n += 1

        return s       # return string
    
    def addMove(self, col, ox ):
        #find the first row in the column
        #without a checker in it and
        #then add the ox checker there...
        #do this by checking values
        #in self.data...
        row = self.height
        row -= 1
        height = [self.height]
        for x in range(self.height):
            if self.data[row][col] == ' ':
                self.data[row][col] = ox
                break 
            else:
                row -= 1
        return

    def clear(self):
        for row in range(self.height):
            for col in range(self.width):
                self.data[row][col] = ' '
    
    def delMove(self, col):
        row = 0
        height = [self.height]
        for x in range(self.height):
            if self.data[row][col] != ' ':
                self.data[row][col] = ' '
                break
            else:
                row += 1
    
    def allowsMove(self, col):
        row = 0
        height = [self.height]
        for x in range(self.height):
            if col not in range(self.width):
                return False
            else: 
                if self.data[row][col] == ' ':
                    return True 
                else:
                    return False
            break    

    
    def isFull(self):
        x = 0
        for row in range(self.height):
            for col in range(self.width):
                if self.data[row][col] == ' ':
                    x = 1
                    return False
                    break 
        if x == 0:
            return True   

    def winsFor(self,ox):
        '''checks for win horizonally'''
        x = 0
        for row in range(self.height):
            for col in range(self.width - 3):
                if self.data[row][col] == ox and \
                   self.data[row][col+1] == ox and \
                   self.data[row][col+2] == ox and \
                   self.data[row][col+3] == ox:
                    return True
                    x += 1
                    break
        '''checks for win vertically'''
        for col in range(self.width):
            for row in range(self.height - 3):
                if self.data[row][col] == ox and \
                   self.data[row+1][col] == ox and \
                   self.data[row+2][col] == ox and \
                   self.data[row+3][col] == ox:
                    return True
                    x += 1
                    break
        '''checks height diagonally upwards''' 
        for row in range(self.height): 
            for col in range(self.width-3):
                if self.data[row][col] == ox and \
                   self.data[row-1][col+1] == ox and \
                   self.data[row-2][col+2] == ox and \
                   self.data[row-3][col+3] == ox:
                    return True
                    x += 1
                    break
        '''checks height diagonally downwards'''
        for row in range(self.height):
            for col in range(self.width):
                if self.data[row][col] == ox and \
                   self.data[row-1][col-1] == ox and \
                   self.data[row-2][col-2] == ox and \
                   self.data[row-3][col-3] == ox:
                    return True
                    x += 1
                    break
        # Check for other winning conditions
        if x == 0:
            return False
    
    def hostGame(self): 
        print(self.__repr__())
        x = 1
        while x >= 0:
            if x == 1:
                col = int(input('User "X": '))
                while self.allowsMove(col) == False:
                    col = int(input('Unvalid move, please choose another move:'))
                self.addMove(col, 'X')
                if self.winsFor('X') == True:
                    print('Congrats, User "X" won!')
                    x -= 2
                elif self.isFull() == True:
                    print('Board full, Tie!')
                    x -= 2
                else:
                    x -= 1
                    print(self.__repr__())
            else:
                col = int(input('User "O": '))
                while self.allowsMove(col) == False:
                    col = int(input('Unvalid move, please choose another move:'))
                self.addMove(col, 'O')
                if self.winsFor('O') == True:
                    print('Congrats, User "O" won!')
                    x -= 1
                elif self.isFull() == True:
                    print('Board full, Tie!') 
                    x -= 1              
                else:
                    x += 1
                    print(self.__repr__())
        print(self.__repr__())
    def playGameWith(self, Player):	
        print(self.__repr__())
        x = 1
        while x >= 0:
        	board = self.__repr__();		
            if x == 1:	
        		col = int(input('User "X": '))	
        		while self.allowsMove(col) == False:	
        			col = int(input('Unvalid move, please choose another move:'))	
        		self.addMove(col, 'X')	
        		if self.winsFor('X') == True:	
        			print('Congrats, User "X" won!')	
        			x -= 2	
        		elif self.isFull() == True:	
        			print('Board full, Tie!')
        			x -= 2	
        		else:	
        			x -= 1
        			print(board)	
        	else:
        		oMove = Player.nextMove(self)	
        		print(oMove)
        		self.addMove(oMove, 'O')
		        if self.winsFor('O') == True:
		            print('Congrats, User "O" won!')
		            x -= 1
		        elif self.isFull() == True:
		            print('Board full, Tie!') 
		            x -= 1              
		        else:
		            x += 1
		            print(self.__repr__())
        print(self.__repr__())


class Player():
    
    def __init__(self, ox, tbt, ply):
        self.checker = ox
        self.tieBreakType = tbt
        self.ply = ply
    def scoresFor(self, b, ox, ply): # b = updated board
        colScores = []
        for x in range(b.width):
            if b.allowsMove(x) == True:
                b.addMove(x, ox)
                if b.winsFor(ox) == True:
                    # add score 100 to a LIST
                    colScores.append(100)
                else:
                    if ply > 1:
                        # compute scores for opponet with ply -1 
                            # for each ply, if theres no win for the first time, re run this
                            # function for each ply...
                        # THEN add 100 to opponets best score as we will want 
                        # to block out their potential win
                        # must switch back and forth with ox depending on the ply
                        if ox == 'O':
                            newox = 'X'
                        else:
                            newox = 'O'
                            # checks for that specific column
                            # add score of 100 (opponets score)
                        plyList = self.scoresFor(b, newox, ply -1)
                        colScores.append(100 - max(plyList))
                    else:
                        # add score of 50 onto a LIST (append)
                        colScores.append(50)
                # remove from board
                b.delMove(x)
            else:
                colScores.append(-1)

        return colScores
        # Basically it updates a singular list depending on how many ply given
        # Now, from that list, find the highest number (100) and return that column
            # for loop ?
        # If there's multiple, do the function tieBreaker() to decided
    '''this function is used in playGameWith to figure out the next move (predicting)'''
    def nextMove(self, board):
        # grab the list from scoresFor and finds the max
        # From that max, find the column number
        # use index(); first, find max, then assign it to a varable
            # use that variable in mylist.index() to find position
            # b.addmove(col, ox)
        # must also check for tie breakers if theres multiple maxes
        ox = self.checker
        ply = self.ply
        tbt = self.tieBreakType
        myList = self.scoresFor(board, ox, ply)
        if tbt == 'Left':
            '''Left Tiebreaker'''
            ind = max(myList)
            return myList.index(ind)
            ''' Right tiebreaker '''
            # run through the list and update the index for every max
            # after the loop, the index will be update to the right most
        elif tbt == 'Right':
            maxList = []
            for x in range(len(myList)):
                if myList[x] == max(myList):
                    maxList.append(x)
            return maxList[-1]
        else:
            maxList = []
            for x in range(len((myList))):
                if myList[x] == max(myList):
                    maxList.append(x)
            return random.choice(maxList)


    '''the end function..?'''
    #def playGameWith(self, aiPlayer):

class TkTTT:

    def __init__(self, window, board, player):
        self.window = window
        self.board = board 
        self.player = player

        self.diameter = 100
        self.height = self.board.height * self.diameter
        self. width = self.board.width * self.diameter

        frame = Frame(window)
        frame.pack()
        frame.configure(bg='#895F4E')

        # quit
        quitButton = Button(frame, text="Exit Game", command=self.quitGame)
        quitButton.pack()

        # main play space
        self.canvas = Canvas(frame, height=self.height+100, width=self.width, background='#C8C8C8')
        self.canvas.bind('<Button-1>', self.mouseInput)
        self.canvas.pack()
        self.canvas.configure(bg='#F1E7E3')

        self.circles = []   # play space

        for row in range(self.board.height):
            circleRow = []
            for col in range (self.board.width):
                x = col * self.diameter
                y = row * self.diameter
                circle = self.canvas.create_oval(x, y, x + self.diameter, y + self.diameter)
                circleRow.append(circle)
            self.circles.append(circleRow)

        self.boardCheck = []
        for row in range(self.board.height):
            circleRow = []
            for col in range (self.board.width):
                circleRow.append(' ')
            self.boardCheck.append(circleRow)
        print(self.boardCheck)

        # message at bottom of board ; can be adjusted
        self.message = self.canvas.create_text(self.width/2, self.height+50, text="Click a Row to Start Game!", fill='black')

    def quitGame(self):
        self.window.destroy()

    def playGameWith(self, Player): 
        print(self.__repr__())
        x = 1
        while x >= 0:
            board = self.__repr__()     
            if x == 1:  
                col = int(input('User "X": '))  
                while self.allowsMove(col) == False:    
                    col = int(input('Unvalid move, please choose another move:'))   
                self.addMove(col, 'X')  
                if self.winsFor('X') == True:   
                    print('Congrats, User "X" won!')    
                    x -= 2  
                elif self.isFull() == True: 
                    print('Board full, Tie!')
                    x -= 2  
                else:   
                    x -= 1
                    print(board)    
            else:
                oMove = Player.nextMove(self)   
                print(oMove)
                self.addMove(oMove, 'O')
                if self.winsFor('O') == True:
                    print('Congrats, User "O" won!')
                    x -= 1
                elif self.isFull() == True:
                    print('Board full, Tie!') 
                    x -= 1              
                else:
                    x += 1
                    print(self.__repr__())
        print(self.__repr__())

    def mouseInput(self,event):
        col = int(event.x / self.diameter)
        row = int(event.y / self.diameter)
        print(event.y, row)
        self.canvas.itemconfig(self.message, text='...')

        if self.board.allowsMove(col) != True:
            messagebox.showinfo('Connect 4', 'Unvalid move, please choose another column')
            return
        self.board.addMove(col, 'X')
        row = int(self.height/self.diameter) - 1 # somehow get the max number of circles
        x = 1
        while x == 1:
            if self.boardCheck[row][col] == ' ':
                self.canvas.itemconfig(self.circles[row][col], fill = 'red')
                self.canvas.update()
                self.boardCheck[row][col] = 'X'
                x -= 1
                print(self.board.__repr__)
            else:
                row -= 1

        if self.board.winsFor('X'):
            messagebox.showinfo('Connect 4', 'Game Over! You Win!')
            self.quitGame()
            return

        elif self.board.isFull():
            messagebox.showinfo('Connect 4', 'Game Over! Tie!')
            self.quitGame()
            return

        ''' AI moves '''
        (col) = self.player.nextMove(self.board)

        self.board.addMove(col, 'O')
        row = int(self.height / self.diameter) - 1 # somehow get the max number of circles
        x = 1
        while x == 1:
            if self.boardCheck[row][col] == ' ':
                for i in range (200, -1, -20):
                    color = '#{:02x}{:02x}{:02x}'.format(i,i,i)
                    self.canvas.itemconfig(self.circles[row][col], fill=color)
                    self.canvas.update()
                    sleep(0.05)
                self.boardCheck[row][col] = 'O'
                self.canvas.itemconfig(self.message, text='Your Turn!')
                x -= 1
                print(self.board.__repr__)
            else:
                row -= 1

        if self.board.winsFor('O'):
            messagebox.showinfo('Connect 4', 'Game Over! You Lose!')
            self.quitGame()
            return
        if self.board.isFull():
            messagebox.showinfo('Connect 4', 'Game Over! Tie!')
            self.quitGame()
            return


def main():
    b = Connect4(7,6)
    p = Player('O', 'Random', 1)

    root = Tk()
    root.title('Connect 4')
    game = TkTTT(root, b, p)
    root.mainloop()

if __name__ == '__main__':
    main()

