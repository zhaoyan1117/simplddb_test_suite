simplddb_test_suite
===================
This is a UPDATED test suite for project of CS186 Fall 2013, a database intro course in UC Berkeley.

About tests
-----------
This test suite includes all the tests from original test suite.

New tests are added with naming convention: "new_methodNameTest".

Usage
-----
Clone or download the repo.

Please replace the original **test** directory **build.xml** file with the file/directory in this repo. 

Then run tests.

run unit tests: `ant test`

run system tests: `ant systemtest`

run specific unit test: `ant runtest -Dtest=?` ?=testName

run specific system test: `ant runsystest -Dtest=?` ?=testName

run all tests and generate html test report: `ant test-report`
