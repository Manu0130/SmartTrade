async function verifyAccount() {

    const user_dto = {
        verification: document.getElementById("verification").value,
        
    };

    const response = await fetch(
            "Verification",
            {
                method: "POST",
                body: JSON.stringify(user_dto),
                headers: {
                    "Content-Type": "application/json"
                }
            }
    );

    if (response.ok) {

        const json = await response.json();


        if (json.success) {
            window.location = "index.html";

        } else {


            document.getElementById("message").innerHTML = json.content;


        }


    } else {

        document.getElementById("message").innerHTML = "Please Try Again Later";

    }
}


