
// function getUserFromDb
export async function getUserFromDb(email: string, password: string) {
    // Your logic to verify if the user exists
    // Call your API from here

    console.log("getUserFromDb", email, password)
    return {
        userId: 1,
        username: "John Doe",
        email: email,
        role: "admin",
    }
}