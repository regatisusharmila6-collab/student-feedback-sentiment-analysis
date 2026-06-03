import pandas as pd
import nltk
import string

from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize

from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.model_selection import train_test_split
from sklearn.naive_bayes import MultinomialNB
from sklearn.metrics import accuracy_score

# -----------------------------
# DOWNLOAD NLTK DATA
# -----------------------------
nltk.download('punkt')
nltk.download('stopwords')
nltk.download('punkt_tab')   # important fix

# -----------------------------
# 1. SAMPLE DATASET (IMPROVED)
# -----------------------------
data = {
    "text": [
        "The teaching was really good",
        "The assignments were too difficult",
        "I loved the course",
        "The class was boring",
        "The teacher explained very well",
        "Too much homework and stress",
        "Amazing experience",
        "Not helpful at all",
        "The course was interesting but tough",
        "Good teaching but lots of assignments",
        "Very bad explanation",
        "Excellent teaching and easy assignments"
    ],
    "sentiment": [
        "Positive",
        "Negative",
        "Positive",
        "Negative",
        "Positive",
        "Negative",
        "Positive",
        "Negative",
        "Mixed",
        "Mixed",
        "Negative",
        "Positive"
    ]
}

df = pd.DataFrame(data)

# -----------------------------
# 2. TEXT PREPROCESSING
# -----------------------------
def preprocess(text):
    text = text.lower()
    text = text.translate(str.maketrans('', '', string.punctuation))
    
    words = word_tokenize(text)
    
    stop_words = set(stopwords.words('english'))
    words = [w for w in words if w not in stop_words]
    
    return " ".join(words)

df["clean_text"] = df["text"].apply(preprocess)

# -----------------------------
# 3. TEXT → NUMBERS (TF-IDF)
# -----------------------------
vectorizer = TfidfVectorizer()
X = vectorizer.fit_transform(df["clean_text"])

y = df["sentiment"]

# -----------------------------
# 4. TRAIN MODEL
# -----------------------------
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)

model = MultinomialNB()
model.fit(X_train, y_train)

# -----------------------------
# 5. TEST MODEL
# -----------------------------
y_pred = model.predict(X_test)
print("Accuracy:", accuracy_score(y_test, y_pred))

# -----------------------------
# 6. IMPROVED EMOTION DETECTION
# -----------------------------
def detect_emotion(text):
    text = text.lower()
    emotions = []

    if "good" in text or "amazing" in text or "love" in text or "excellent" in text:
        emotions.append("Satisfaction")

    if "difficult" in text or "stress" in text or "hard" in text or "tough" in text:
        emotions.append("Frustration")

    if "boring" in text:
        emotions.append("Boredom")

    if "bad" in text:
        emotions.append("Disappointment")

    if len(emotions) == 0:
        return "Neutral"

    return " + ".join(emotions)

# -----------------------------
# 7. USER INPUT LOOP
# -----------------------------
while True:
    user_input = input("\nEnter student feedback: ")

    cleaned = preprocess(user_input)
    vectorized = vectorizer.transform([cleaned])

    # -----------------------------
    # MIXED SENTIMENT LOGIC
    # -----------------------------
    if "but" in user_input.lower():
        sentiment = "Mixed"
    else:
        sentiment = model.predict(vectorized)[0]

    emotion = detect_emotion(user_input)

    print("Sentiment:", sentiment)
    print("Emotion:", emotion)
    