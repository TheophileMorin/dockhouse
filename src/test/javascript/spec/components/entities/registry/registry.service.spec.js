'use strict';

describe('Services Tests', function () {
    var registryService, httpBackend, restangular;

    beforeEach(module('dockhouseApp'));

    describe('Registry', function(){
        var $httpBackend, registryService, restangular;

        beforeEach(inject(function($injector, Registry, Restangular) {
            $httpBackend = $injector.get('$httpBackend');

            registryService = Registry;
            httpBackend = $httpBackend;
            restangular = Restangular;

            //$httpBackend.expectGET(/api\/account\?cacheBuster=\d+/).respond(200, '');
            //$httpBackend.expectGET('i18n/en/global.json').respond(200, '');
            //$httpBackend.expectGET('i18n/en/language.json').respond(200, '');
            //$httpBackend.expectGET('scripts/components/navbar/navbar.html').respond({});
            //$httpBackend.expectGET('i18n/en/global.json').respond(200, '');
            //$httpBackend.expectGET('i18n/en/language.json').respond(200, '');
            //$httpBackend.expectGET('i18n/en/main.json').respond(200, '');
            //$httpBackend.expectGET('scripts/app/main/main.html').respond({});
        }));

        //make sure no expectations were missed in your tests.
        //(e.g. expectGET or expectPOST)
        afterEach(function() {
            //$httpBackend.verifyNoOutstandingExpectation();
            //$httpBackend.verifyNoOutstandingRequest();
        });


        ///////////// TESTS //////////////

        it('Should be defined', function() {
            expect(registryService).not.toBeNull();
        });

        it("should contain a getAll() function", function () {
            expect(registryService.getAll).not.toBeNull();
        });

        it("should contain a get() function", function () {
            expect(registryService.get).not.toBeNull();
        });

        it("should contain a create() function", function () {
            expect(registryService.create).not.toBeNull();
        });

        it("should contain a update() function", function () {
            expect(registryService.update).not.toBeNull();
        });

        it("should contain a remove() function", function () {
            expect(registryService.remove).not.toBeNull();
        });


        it("should respond with an registry from the get()", function() {
            var id = 1;

            var registry = {
                "id" : 1,
                "name" : "Registery 1",
                "type" : "Docker",
                "host" : "localhost",
                "port" : "9000",
                "protocole" : "http"
            };

            httpBackend.whenGET(/\/dockhouse\/api\/registries\/1\?cacheBuster=\d+/).respond(registry);
            registryService.get(id).then(function(data) {
                expect(data.name).toBe(registry.name);
                expect(data.type).toBe(registry.type);
            });
        });


        it("should respond with array of registries from the getAll()", function() {
            var arrayRegistries = [
                {
                    "id" : 1,
                    "name" : "Registery 1",
                    "type" : "Docker",
                    "host" : "localhost",
                    "port" : "9000",
                    "protocole" : "http"
                },
                {
                    "id" : 2,
                    "name" : "Registery 2",
                    "type" : "Docker",
                    "host" : "localhost",
                    "port" : "9000",
                    "protocole" : "https"
                }
            ];

            httpBackend.whenGET(/\/dockhouse\/api\/registries\?cacheBuster=\d+/).respond(arrayRegistries);
            registryService.getAll().then(function(data) {
                expect(data.length).toBe(2);
                expect(data[0].name).toBe(arrayRegistries[0].name);
                expect(data[1].name).toBe(arrayRegistries[1].name);
            });
        });


        it("should respond with the status CREATED for create()", function() {
            var registry =
            {
                "name" : "Registry 1",
                "host" : "localhost",
                "port" : "9000",
                "protocol" : "http"
            };

            var responseExpected = {
                "name" : "Registry 1",
                "host" : "localhost",
                "port" : "9000",
                "protocol" : "http",
                "status" : 201
            };

            httpBackend.whenPOST(/\/dockhouse\/api\/registries\?cacheBuster=\d+/, registry).respond(responseExpected);
            registryService.create(registry).then(function(data) {
                expect(data.status).toBe(responseExpected.status);
            });
        });

        it("should respond with the status OK for update()", function() {

            var payloadExpected =
            {
                "id" : 1,
                "name" : "Registry 1",
                "host" : "localhost",
                "port" : "9000",
                "protocol" : "http"
            };

            // Construct the Restangular object
            var registry = restangular.one('registries',1);
            registry.name = "Registry 1";
            registry.host = "localhost";
            registry.port = "9000";
            registry.protocol = "http";

            var responseExpected = {
                "status" : 200
            };

            httpBackend.whenPUT(/\/dockhouse\/api\/registries\?cacheBuster=\d+/, payloadExpected).respond(responseExpected);
            registryService.update(registry).then(function(data) {
                expect(data.status).toBe(responseExpected.status);
            });
        });
    });

});

