#
# StarshipOS Copyright (c) 2009-2025. R.A. James
#

#!/bin/ksh
#
# needs ksh to run the script.
OPENSSL=openssl

# generate a self-signed root certificate
if [ ! -f root/root_cert.pem ]; then
    if [ ! -d root ]; then
        mkdir root
    fi

    ${OPENSSL} req -x509 -newkey rsa:1024 -keyout root/root_key.pem \
        -out root/root_cert.pem -subj "/C=US/O=Example" \
        -config openssl.cnf -reqexts cert_issuer -days 7650 \
        -passin pass:passphrase -passout pass:passphrase
fi

# generate subca cert issuer
if [ ! -f subca/subca_cert.pem ]; then
    if [ ! -d subca ]; then
        mkdir subca
    fi

    ${OPENSSL} req -newkey rsa:1024 -keyout subca/subca_key.pem \
        -out subca/subca_req.pem -subj "/C=US/O=Example/OU=Class-1" \
        -days 7650 -passin pass:passphrase -passout pass:passphrase

    ${OPENSSL} x509 -req -in subca/subca_req.pem -extfile openssl.cnf \
        -extensions cert_issuer -CA root/root_cert.pem \
        -CAkey root/root_key.pem -out subca/subca_cert.pem -CAcreateserial \
        -CAserial root/root_cert.srl -days 7200 -passin pass:passphrase
fi

# generate certifiacte for Alice
if [ ! -f subca/alice/alice_cert.pem ]; then
    if [ ! -d subca/alice ]; then
        mkdir -p subca/alice
    fi

    ${OPENSSL} req -newkey rsa:1024 -keyout subca/alice/alice_key.pem \
        -out subca/alice/alice_req.pem \
        -subj "/C=US/O=Example/OU=Class-1/CN=Alice" -days 7650 \
        -passin pass:passphrase -passout pass:passphrase

    ${OPENSSL} x509 -req -in subca/alice/alice_req.pem \
        -extfile openssl.cnf -extensions alice_of_subca \
        -CA subca/subca_cert.pem -CAkey subca/subca_key.pem \
        -out subca/alice/alice_cert.pem -CAcreateserial \
        -CAserial subca/subca_cert.srl -days 7200 -passin pass:passphrase
fi

# generate certifiacte for Bob
if [ ! -f subca/bob/bob.pem ]; then
    if [ ! -d subca/bob ]; then
        mkdir -p subca/bob
    fi

    ${OPENSSL} req -newkey rsa:1024 -keyout subca/bob/bob_key.pem \
        -out subca/bob/bob_req.pem \
        -subj "/C=US/O=Example/OU=Class-1/CN=Bob" -days 7650 \
        -passin pass:passphrase -passout pass:passphrase

    ${OPENSSL} x509 -req -in subca/bob/bob_req.pem \
        -extfile openssl.cnf -extensions ee_of_subca \
        -CA subca/subca_cert.pem -CAkey subca/subca_key.pem \
        -out subca/bob/bob_cert.pem -CAcreateserial \
        -CAserial subca/subca_cert.srl -days 7200 -passin pass:passphrase
fi

# generate certifiacte for Susan
if [ ! -f subca/susan/susan_cert.pem ]; then
    if [ ! -d subca/susan ]; then
        mkdir -p subca/susan
    fi

    ${OPENSSL} req -newkey rsa:1024 -keyout subca/susan/susan_key.pem \
        -out subca/susan/susan_req.pem \
        -subj "/C=US/O=Example/OU=Class-1/CN=Susan" -days 7650 \
        -passin pass:passphrase -passout pass:passphrase

    ${OPENSSL} x509 -req -in subca/susan/susan_req.pem \
        -extfile openssl.cnf -extensions susan_of_subca \
        -CA subca/subca_cert.pem -CAkey subca/subca_key.pem \
        -out subca/susan/susan_cert.pem -CAcreateserial \
        -CAserial subca/subca_cert.srl -days 7200 -passin pass:passphrase
fi

