
import pandas as pd

nombres = ['age','sex','cp','trestbps','chol','fbs','restecg','thalach','exang','oldpeak','slope','ca','thal','class']

dataset = pd.read_csv('Heart_Disease_Data.csv',na_values="?")

#dataset=pd.read_csv("C:\Users\mahmoud\Downloads\heartdiseaseensembleclassifier (1)", na_values="?")
#dataset = pd.read_csv('http://archive.ics.uci.edu/ml/machine-learning-databases/heart-disease/cleve.mod', skiprows=20, header=None, sep='\s+', names=nombres, index_col=False, na_values="?")
dataset["pred_attribute"].replace(inplace=True, value=[3], to_replace=[4])
dataset


%matplotlib inline

import numpy as np
import pandas as pd
from scipy import stats, integrate
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.linear_model import LogisticRegression 
from sklearn.model_selection import train_test_split 
from sklearn import metrics
dataset.dropna(inplace=True, axis=0, how="any")
X=dataset.loc[:, "age":"thal" ]
Y=dataset["pred_attribute"]

# evaluate the model by splitting into train and test sets

X_train, X_test, Y_train, Y_test = train_test_split(X, Y, test_size=0.3, random_state=0)


# instantiate a logistic regression model, and fit with X and y (with training data in X,y)
model = LogisticRegression()
model.fit(X_train, Y_train)

# check the accuracy on the training set
model.score(X_train, Y_train)

# check the accuracy on the test set
model.score(X_test, Y_test)

# predict class labels for the training set
predicted1 = model.predict(X_train)

# predict class labels for the test set
predicted2 = model.predict(X_test)

pd.crosstab(Y_train, predicted1, rownames=['Predicted'], colnames=['Reality'], margins=True)


pd.crosstab(Y_test, predicted2, rownames=['Predicted'], colnames=['Reality'], margins=True)







#################################################

#Support Vector Machine


from sklearn.svm import SVC
model = SVC()
model.fit(X_train, Y_train)
print("Accuracy on training set: {:.2f}".format(model.score(X_train, Y_train)))
print("Accuracy on test set: {:.2f}".format(model.score(X_test, Y_test)))

###scaling
from sklearn.preprocessing import MinMaxScaler
scaler = MinMaxScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.fit_transform(X_test)
model = SVC()
model.fit(X_train_scaled, Y_train)
print("Accuracy on training set: {:.2f}".format(model.score(X_train_scaled, Y_train)))
print("Accuracy on test set: {:.2f}".format(model.score(X_test_scaled, Y_test)))


model = SVC(C=1000)
model.fit(X_train_scaled, Y_train)
print("Accuracy on training set: {:.3f}".format(
    model.score(X_train_scaled, Y_train)))
print("Accuracy on test set: {:.3f}".format(model.score(X_test_scaled, Y_test)))





###############################################################################







###Deep Learning###



from sklearn.neural_network import MLPClassifier
model= MLPClassifier(random_state=42)
model.fit(X_train, Y_train)
print("Accuracy on training set: {:.2f}".format(model.score(X_train, Y_train)))
print("Accuracy on test set: {:.2f}".format(model.score(X_test, Y_test)))


#$caling

from sklearn.preprocessing import StandardScaler
scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.fit_transform(X_test)
model = MLPClassifier(random_state=0)
model.fit(X_train_scaled, Y_train)
print("Accuracy on training set: {:.3f}".format(
    model.score(X_train_scaled, Y_train)))
print("Accuracy on test set: {:.3f}".format(model.score(X_test_scaled, Y_test)))
#

predicted1 = model.predict(X_train_scaled)

# predict class labels for the test set
predicted2 = model.predict(X_test_scaled)

pd.crosstab(Y_train, predicted1, rownames=['Predicted'], colnames=['Reality'], margins=True)


pd.crosstab(Y_test, predicted2, rownames=['Predicted'], colnames=['Reality'], margins=True)


#
#increase #of iterations

model = MLPClassifier(max_iter=1000, random_state=0)
model.fit(X_train_scaled, Y_train)
print("Accuracy on training set: {:.3f}".format(
    model.score(X_train_scaled, Y_train)))
print("Accuracy on test set: {:.3f}".format(model.score(X_test_scaled, Y_test)))




model = MLPClassifier(max_iter=1000, alpha=1, random_state=0)
model.fit(X_train_scaled, Y_train)
print("Accuracy on training set: {:.3f}".format(
    model.score(X_train_scaled, Y_train)))
print("Accuracy on test set: {:.3f}".format(model.score(X_test_scaled, Y_test)))




###############################################################################


#################
#Decision Tree

from sklearn.tree import DecisionTreeClassifier
model = DecisionTreeClassifier(random_state=0)
model.fit(X_train, Y_train)
print("Accuracy on training set: {:.3f}".format(model.score(X_train, Y_train)))
print("Accuracy on test set: {:.3f}".format(model.score(X_test, Y_test)))


model = DecisionTreeClassifier(max_depth=3, random_state=0)
model.fit(X_train, Y_train)
print("Accuracy on training set: {:.3f}".format(model.score(X_train, Y_train)))
print("Accuracy on test set: {:.3f}".format(model.score(X_test, Y_test)))

print("Feature importances:\n{}".format(model.feature_importances_))

#Random Forest
def plot_feature_importances_diabetes(model):
    plt.figure(figsize=(8,6))
    n_features = 8
    plt.barh(range(n_features), model.feature_importances_, align='center')
    plt.yticks(np.arange(n_features), diabetes_features)
    plt.xlabel("Feature importance")
    plt.ylabel("Feature")
    plt.ylim(-1, n_features)
plot_feature_importances_diabetes(model)
plt.savefig('feature_importance')


from sklearn.ensemble import RandomForestClassifier
model = RandomForestClassifier(n_estimators=100, random_state=0)
model.fit(X_train, Y_train)
print("Accuracy on training set: {:.3f}".format(model.score(X_train, Y_train)))
print("Accuracy on test set: {:.3f}".format(model.score(X_test, Y_test)))

#####################################################################################
#####################################################################################
#####################################################################################
#####################################################################################
model1 = RandomForestClassifier(max_depth=3, n_estimators=100, random_state=0)      
model1.fit(X_train, Y_train)
print("Accuracy on training set: {:.3f}".format(model1.score(X_train, Y_train)))    
print("Accuracy on test set: {:.3f}".format(model1.score(X_test, Y_test)))

#####################################################################################
#####################################################################################
#####################################################################################
#####################################################################################
plot_feature_importances_diabetes(model)




#Gradient Boosting


from sklearn.ensemble import GradientBoostingClassifier
model = GradientBoostingClassifier(random_state=0)
model.fit(X_train, Y_train)
print("Accuracy on training set: {:.3f}".format(model.score(X_train, Y_train)))
print("Accuracy on test set: {:.3f}".format(model.score(X_test, Y_test)))


model1 = GradientBoostingClassifier(random_state=0, max_depth=1)
model1.fit(X_train, Y_train)
print("Accuracy on training set: {:.3f}".format(model1.score(X_train, Y_train)))
print("Accuracy on test set: {:.3f}".format(model1.score(X_test, Y_test)))


model2 = GradientBoostingClassifier(random_state=0, learning_rate=0.01)
model2.fit(X_train, Y_train)
print("Accuracy on training set: {:.3f}".format(model2.score(X_train, Y_train)))
print("Accuracy on test set: {:.3f}".format(model2.score(X_test, Y_test)))

plot_feature_importances_diabetes(model1)


#################################################





