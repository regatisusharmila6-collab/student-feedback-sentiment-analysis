import matplotlib.pyplot as plt
import numpy as np 
import pandas as pd
X=np.linspace(0,5,6)
print(X)
Y=X**2
print(Y)
plt.plot(X,Y)
plt.title("x and y graph ")
plt.xlabel("X-axis")
plt.ylabel("Y-axis")
plt.show()
plt.subplot(2,2,3)
plt.plot(X,Y)
plt.subplot(2,2,2)
plt.plot(X,X)
plt.show()
plt.subplot(6,6,1)
plt.plot(X,X)
plt.show()
a=[1,2,3,4,5]
b=[2,4,6,8,10]
figure,ax=plt.subplots(2,2)
ax[0,0].plot(a,b)
ax[0,0].set_title("graph one ")

ax[2,2].bar(a,b)
ax[2,2].set_title("graph two")
plt.show()