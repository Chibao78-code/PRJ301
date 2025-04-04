<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <style>
        footer {
            background-color: #2c3e50;
            color: white;
            text-align: center;
            padding: 20px;
            width: 100%;
           
        }
        .footer-content {
            max-width: 1200px;
            margin: 0 auto;
            display: flex;
            justify-content: space-between;
            flex-wrap: wrap;
        }
        .footer-section {
            flex: 1;
            min-width: 200px;
            margin: 10px;
        }
        .footer-section h3 {
            font-size: 16px;
            margin-bottom: 10px;
        }
        .footer-section p {
            margin: 5px 0;
        }
        .footer-section a {
            color: #3498db;
            text-decoration: none;
            display: block;
            margin: 5px 0;
            transition: color 0.3s;
        }
        .footer-section a:hover {
            color: #ecf0f1;
        }
        .copyright {
            font-size: 12px;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <footer>
        <div class="footer-content">
            <div class="footer-section">
                <h3>Contact Us</h3>
                <p>Email: support@onlineexam.com</p>
                <p>Phone: +84 123 456 789</p>
                <p>Address: Số 123  , Saigon, Vietnam</p>
            </div>
            <div class="footer-section">
                <h3>Quick Links</h3>
                <a >Contact</a>
                <a >Help</a>
            </div>
            <div class="footer-section">
                <h3>About Us</h3>
                <p>We are a leading online examination platform dedicated to helping students and educators conduct assessments efficiently and securely.</p>
            </div>
        </div>
        <div class="copyright">
             Online Examination System. 
        </div>
    </footer>
</body>
</html>