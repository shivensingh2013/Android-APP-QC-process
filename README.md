# Quality Check process automation Android Application

Author: Shivendra Singh
Build date: 20th November 2016


Description:This project focuses on modifying a Java based android application for QC employees for faster Product data entry. This app increased the process speed by 6x and reduced data inconsistency by 50%.

Features:
1. Dynamic Form Creation : The form was displayed on the basis of product category and brand selection. The form was directly connected to a online MongoDB server. After the form was filled by QC manager, the data was saved on the online server.

2. Offline Data saving : In case of poor network in warehouse, The QC manager can keep saving the data whcih will be syncronized automatically as soon as the phone receives mobile data connection at a later point in time.

3.Better Data Consisteny: The form fields were updated to introduce dropdown boxes and radio buttons instead of Text boxes. The data for different dropboxes was obtained using publicly available API.

4. Easier Database access: The Mongo DB database was made easily available to other research teams through RESTful APIs created on LARAVEL Framework.

