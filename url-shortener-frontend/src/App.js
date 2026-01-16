import { useState } from "react";

function App() {
  const [longUrl, setLongUrl] = useState("");
  const [shortUrl, setShortUrl] = useState("");

  const shortenUrl = () => {
    if (!longUrl) {
      alert("Enter a URL");
      return;
    }

    fetch("http://localhost:8080/api/url/create", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ longurl: longUrl })
    })
      .then(res => res.json())
      .then(data => {
        console.log("Backend response:", data);
        setShortUrl(data.shortUrl);
      })
      .catch(() => alert("Error"));
  };

  const openUrl = () => {
    window.open(`http://localhost:8080/r/${shortUrl}`, "_blank");
  };

  return (
    <div style={styles.page}>
      <div style={styles.box}>
        <h2 >URL Shortener</h2>

        <input
          style={styles.input}
          type="text"
          placeholder="Enter long URL"
          value={longUrl}
          onChange={(e) => setLongUrl(e.target.value)}
        />

        <button style={styles.button} onClick={shortenUrl}>
          Shorten URL
        </button>

        {shortUrl && (
          <>
            <p>
              Short URL:
              <br />
              <a
                href={`http://localhost:8080/r/${shortUrl}`}
                target="_blank"
                rel="noreferrer"
              >
                http://localhost:8080/r/{shortUrl}
              </a>
            </p>

            <button style={styles.button} onClick={openUrl}>
              Open Short URL
            </button>
          </>
        )}
      </div>
    </div>
  );
}

const styles = {


  page: {
    height: "100vh",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    background: "#f2f2f2",
    fontsize: "50px"
  },
  box: {
    background: "white",
    padding: "20px",
    width: "350px",
    height: "auto",
    borderRadius: "8px",
    textAlign: "center",
    boxShadow: "0 0 10px rgba(0,0,0,0.1)"
  },
  input: {
    width: "100%",
    padding: "5px",
    marginBottom: "10px",
    borderRadius: "4px",
    border: "1px solid #ccc"
  },
  button: {
    width: "100%",
    padding: "8px",
    marginTop: "10px",
    cursor: "pointer"
  }
};

export default App;
