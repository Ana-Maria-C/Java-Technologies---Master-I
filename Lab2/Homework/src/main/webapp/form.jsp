<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Form with CAPTCHA</title>
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f8ff;
            padding: 20px;
        }

        form {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }

        input[type="text"], select {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        input[type="submit"] {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 12px 20px;
            font-size: 16px;
            cursor: pointer;
            border-radius: 4px;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        h2 {
            text-align: center;
        }

        .g-recaptcha {
            margin-bottom: 20px;
        }
    </style>

</head>
<body>

<form action="form" method="post">

    <div class="form-group">
        <label for="fullName">First Name, Last Name:</label>
        <input type="text" id="fullName" name="fullName" required>
    </div>

    <div class="form-group">
        <label>Did you drink coffee this morning?</label>
        <select name="morningCoffee" required>
            <option value="yes">Yes</option>
            <option value="no">No</option>
            <option value="dontRemember">I do not remember</option>
        </select>
    </div>

    <div class="form-group">
        <label>Do you like this lab?</label>
        <select name="labAppreciation" required>
            <option value="yes">Yes</option>
            <option value="absolutely">Absolutely</option>
            <option value="loveIt">I love it</option>
        </select>
    </div>

    <div class="form-group">
        <label>How well do you think you did on the quiz?</label>
        <select name="quizPerformance" required>
            <option value="well">Well</option>
            <option value="veryWell">Very well</option>
            <option value="excellent">Excellent</option>
        </select>
    </div>

    <!-- Add reCAPTCHA  -->
    <div class="g-recaptcha" data-sitekey="6LekPWAqAAAAAD-2dZPm4CwK9-XK0PiSp6DPEIvA"></div>

    <input type="submit" value="Submit">
</form>

</body>
</html>
